package app.kimyeonjung.visionmirror.data.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.kimyeonjung.visionmirror.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherManager {

    private OnFinishCallback callback;
    public void fetchWeather(Context context, OnFinishCallback callback) {
        this.callback = callback;
        new FetchWeather().execute(context);
    }

    public interface OnFinishCallback {
        void done(WeatherItem result);
    }


    @SuppressLint("StaticFieldLeak")
    private class FetchWeather extends AsyncTask<Context, Integer, WeatherItem> {
        private final Map<String, Integer> iconResources = new HashMap<String, Integer>() {{
            put("clear-day", R.drawable.clear_day);
            put("clear-night", R.drawable.clear_night);
            put("cloudy", R.drawable.cloudy);
            put("fog", R.drawable.fog);
            put("partly-cloudy-day", R.drawable.partly_cloudy_day);
            put("partly-cloudy-night", R.drawable.partly_cloudy_night);
            put("rain", R.drawable.rain);
            put("sleet", R.drawable.sleet);
            put("snow", R.drawable.snow);
            put("wind", R.drawable.wind);
        }};

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected WeatherItem doInBackground(Context... params) {
            WeatherItem weatherItem = new WeatherItem();
            OkHttpClient client = new OkHttpClient();

            Location location = new Location("");
            location.setLatitude(37.4536);
            location.setLongitude(126.7317);

            // IP로 부터 위치 가져오기 (위치정보 권한을 사용하기 싫어서....)
            Request request = new Request.Builder().url("http://ip-api.com/json").build();

            try (Response response = client.newCall(request).execute()) {
                JSONObject responseJson = new JSONObject(response.body().string());
                location.setLatitude(responseJson.getDouble("lat"));
                location.setLongitude(responseJson.getDouble("lon"));
                weatherItem.setCity(responseJson.getString("city"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 날씨 정보 가져오기
            String url = "https://api.darksky.net/forecast/"
                    + params[0].getResources().getString(R.string.darksky_key)
                    + "/" + location.getLatitude() + "," + location.getLongitude();

            request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                JSONObject data = new JSONObject(response.body().string());
                data = data.getJSONObject("currently");

                weatherItem.setSummary(data.getString("summary"));
                weatherItem.setTemperature(data.getDouble("temperature"));
                weatherItem.setImageId(iconResources.get(data.getString("icon")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return weatherItem;
        }

        @Override
        protected void onPostExecute(WeatherItem result) {
            super.onPostExecute(result);
            callback.done(result);
        }
    }
}
