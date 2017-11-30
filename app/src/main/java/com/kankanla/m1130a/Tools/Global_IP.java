package com.kankanla.m1130a.Tools;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kankanla on 2017/11/30.
 */

public class Global_IP {
    protected Context context;
    protected backup backup;
    public static String IP_API = "http://ip-api.com/json";


    public interface backup{
        public void back(JSONObject jsonObject);
    }

    public Global_IP(Context context,backup backup) {
        this.context = context;
        this.backup = backup;
    }

    public void t1() {
        GIP_info gip_info = new GIP_info();
        gip_info.execute(new String[]{IP_API});
    }

    private class GIP_info extends AsyncTask<String, Integer, JSONObject> {

        private JSONObject jsonObject;
        private InputStream inputStream;
        private ByteArrayOutputStream byteArrayOutputStream;

        public GIP_info() {
            jsonObject = new JSONObject();
            byteArrayOutputStream = new ByteArrayOutputStream();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10 * 1000);
                httpURLConnection.setConnectTimeout(10 * 1000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }

                if (inputStream != null) {
                    byte[] buffer = new byte[32];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, len);
                    }
                }

                if (byteArrayOutputStream != null) {
                    try {
                        jsonObject = new JSONObject(byteArrayOutputStream.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MalformedURLException e) {
                //URL
                e.printStackTrace();
                try {
                    jsonObject.put("error", "URL Exception-----------------------------------------------");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                //HttpURLConnection
                e.printStackTrace();
                try {
                    jsonObject.put("error", "HttpURLConnection Exception-----------------------------------");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            backup.back(jsonObject);
            super.onPostExecute(jsonObject);
        }
    }

}
