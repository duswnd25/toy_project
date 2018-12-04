package app.kimyeonjung.visionmirror.data.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import app.kimyeonjung.visionmirror.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsManager {

    private OnFinishCallback callback;

    public void fetchNews(Context context, OnFinishCallback callback) {
        this.callback = callback;
        new FetchNews().execute(context);
    }

    public interface OnFinishCallback {
        void done(String[] result);
    }


    @SuppressLint("StaticFieldLeak")
    private class FetchNews extends AsyncTask<Context, Integer, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Context... params) {
            String[] result = {"", ""};
            try {
                Document doc = Jsoup.connect(params[0].getResources().getString(R.string.news_url)).get();
                result[0] = doc.select("item").eq(0).select("title").eq(0).text();
                result[1] = doc.select("item").eq(0).select("link").eq(0).text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            callback.done(result);
        }
    }
}
