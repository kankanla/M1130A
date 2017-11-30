package com.kankanla.m1130a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.kankanla.m1130a.Tools.Global_IP;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.ip);

        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");

        Global_IP global_ip = new Global_IP(this, new Global_IP.backup() {
            @Override
            public void back(JSONObject jsonObject) {
                System.out.println("back-----------------------------------------------");
                System.out.println(jsonObject);
                try {
                    textView.setText(jsonObject.getString("query"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("back-----------------------------------------------");
            }
        });
        global_ip.t1();
    }
}
