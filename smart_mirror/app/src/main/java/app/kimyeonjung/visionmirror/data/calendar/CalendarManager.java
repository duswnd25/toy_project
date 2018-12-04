package app.kimyeonjung.visionmirror.data.calendar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class CalendarManager {
    private Context context;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 3;

    public CalendarManager(Context context) {
        this.context = context;
    }

    public void getData() {

    }

    private void requestCameraPermission() {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions((Activity) context, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = (Activity) context;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };
    }
}
