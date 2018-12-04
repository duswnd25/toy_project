package app.kimyeonjung.visionmirror.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.squareup.otto.Subscribe;

import java.io.IOException;

import app.kimyeonjung.visionmirror.R;
import app.kimyeonjung.visionmirror.core.TTSActivity;
import app.kimyeonjung.visionmirror.core.bus.BusProvider;
import app.kimyeonjung.visionmirror.core.bus.FaceDetectEvent;
import app.kimyeonjung.visionmirror.core.bus.FaceDismissEvent;
import app.kimyeonjung.visionmirror.data.calendar.CalendarManager;
import app.kimyeonjung.visionmirror.data.camera_vision.CameraSourcePreview;
import app.kimyeonjung.visionmirror.data.camera_vision.GraphicFaceTrackerFactory;
import app.kimyeonjung.visionmirror.data.camera_vision.GraphicOverlay;
import app.kimyeonjung.visionmirror.data.greeting.GreetingManager;
import app.kimyeonjung.visionmirror.data.news.NewsManager;
import app.kimyeonjung.visionmirror.data.weather.WeatherItem;
import app.kimyeonjung.visionmirror.data.weather.WeatherManager;
import me.grantland.widget.AutofitTextView;

/**
 * 카메라 뷰를 위한 액티비티. 카메라를 통해 인식한 각각의 얼굴의 정보 (id, position, size, happiness)를
 * 화면에 표시한다.
 */
public final class MainActivity extends TTSActivity implements View.OnClickListener {

    // =================================== CAMERA ==============================
    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // =================================== GREETING =============================
    private static boolean isFirstCatch = true;
    private static SharedPreferences pref;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        BusProvider.getInstance().register(this);

        initUI();

        // 권한을 확인한다.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }
    }

    private void initUI() {
        pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        // 이 부분을 권한 확인 이전에 해야 정상적으로 얼굴이 인식된다.
        preview = findViewById(R.id.preview);
        graphicOverlay = findViewById(R.id.faceOverlay);

        initWeatherView();
        initNews();

        // =================================== SETTING ===============================
        ImageView settingView = findViewById(R.id.setting);
        settingView.setOnClickListener(this);
    }

    // =================================== CALENDAR ==================================
    private void initCalendar() {
        new CalendarManager(this).getData();
    }

    // =================================== WEATHER ===================================
    private void initNews() {
        new NewsManager().fetchNews(this, new NewsManager.OnFinishCallback() {
            @Override
            public void done(final String[] result) {
                AutofitTextView newsView = findViewById(R.id.information_news);
                newsView.setText(result[0]);
                newsView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result[1]));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    // =================================== WEATHER ===================================
    private void initWeatherView() {
        new WeatherManager().fetchWeather(this, new WeatherManager.OnFinishCallback() {
            @Override
            public void done(WeatherItem result) {
                // 도시
                ((AutofitTextView) findViewById(R.id.weather_city_text)).setText(result.getCity());

                // 이미지
                ((ImageView) findViewById(R.id.weather_image)).setImageResource(result.getImageId());

                // 온도
                AutofitTextView temperatureView = findViewById(R.id.weather_temp_text);
                temperatureView.setText(result.getTemperature(MainActivity.this, pref.getBoolean(getString(R.string.use_temp_type_c_pref), true)));

                ((AutofitTextView) findViewById(R.id.weather_summary_text)).setText(result.getSummary());
            }
        });
    }

    // ================================ GREETING & TIME ==============================
    private void initInformationView(String happinessMessage) {
        final LinearLayout greetingContainer = findViewById(R.id.greeting_container);
        final LinearLayout informationContainer = findViewById(R.id.information_container);
        final AutofitTextView greetingMessageView = findViewById(R.id.greeting_greeting_message);
        final AutofitTextView happinessMessageView = findViewById(R.id.greeting_happiness_messsage);

        informationContainer.setVisibility(View.GONE);
        greetingContainer.setVisibility(View.VISIBLE);

        String greetingMessage = new GreetingManager(this).getRandomGreetingMessage();
        greetingMessageView.setText(greetingMessage);
        happinessMessageView.setText(happinessMessage);

        // 음성출력
        if (pref.getBoolean(getString(R.string.use_tts_pref), true)) {
            readTextFlush(greetingMessage + "\n\n" + happinessMessage);
        }

        Handler handler = new Handler(getMainLooper()) {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        greetingMessageView.setText("");
                        happinessMessageView.setText("");
                        greetingContainer.setVisibility(View.GONE);
                        informationContainer.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        handler.sendEmptyMessageDelayed(0, 4000);
    }

    // ===================================== CAMERA ==================================

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(graphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, listener)
                .show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory(graphicOverlay))
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
        }

        cameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name))
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    //==============================================================================================
    // Face Detect/Dismiss
    //==============================================================================================

    @Subscribe
    public void faceUpdate(FaceDetectEvent event) {
        if (isFirstCatch) {
            isFirstCatch = false;
            initInformationView(event.getHappinessMessage(MainActivity.this));
        }
    }

    @Subscribe
    public void faceDismiss(FaceDismissEvent event) {
        Handler dismissHandler = new Handler(getMainLooper()) {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (isFirstCatch) {
                    isFirstCatch = false;
                }
            }
        };
        dismissHandler.sendEmptyMessageDelayed(0, 30000);
    }
}
