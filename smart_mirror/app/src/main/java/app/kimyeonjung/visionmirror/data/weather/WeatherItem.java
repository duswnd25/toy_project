package app.kimyeonjung.visionmirror.data.weather;

import android.content.Context;

import app.kimyeonjung.visionmirror.R;

public class WeatherItem {
    private String summary;
    private int imageId;
    private double temperature;
    private String city;

    WeatherItem() {
    }

    public String getSummary() {
        return summary;
    }

    void setSummary(String summary) {
        this.summary = summary;
    }

    public int getImageId() {
        return imageId;
    }

    void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTemperature(Context context, boolean isUseC) {
        String type = isUseC ? context.getString(R.string.temp_c) : context.getString(R.string.temp_f);
        // 섭씨 화씨 변경
        if (isUseC) {
            temperature = ((temperature - 32) / 1.8);
        }
        return (int) temperature + type;
    }

    void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city.toUpperCase();
    }
}
