package com.example.batterylevel;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.example.batterylevel.flutter_text_view.TextViewPlugin;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

import static android.os.Build.*;

public class MainActivity extends FlutterActivity {
    private static final String CHANEL = "com.example.batterylevel/battery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        TextViewPlugin.registerWith(this);

        new MethodChannel(getFlutterView(), CHANEL).setMethodCallHandler((call, result) -> {
            if(call.method.equals(("getBatteryLevel"))) {
                int batteryLevel = getBatteryLevel();

                if(batteryLevel != -1) {
                    result.success(batteryLevel);
                } else {
                    result.error("UNVAILABLE", "Battery level not available", null);
                }
            } else {
                result.notImplemented();
            }
        });
    }

    private int getBatteryLevel() {
        int batteryLevel = -1;
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(getApplicationContext())
                    .registerReceiver(null, new IntentFilter((Intent.ACTION_BATTERY_CHANGED)));
            batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100)/
                intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }

        return batteryLevel;
    }
}
