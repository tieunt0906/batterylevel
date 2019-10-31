package com.example.batterylevel.flutter_text_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

import static io.flutter.plugin.common.MethodChannel.*;

public class FlutterTextView implements PlatformView, MethodCallHandler {
    private final TextView textView;
    private final MethodChannel methodChannel;

    public FlutterTextView(Context context, BinaryMessenger messenger, int viewId) {
        textView = new TextView(context);
        methodChannel = new MethodChannel(messenger, "com.example.batterylevel/textview_" + viewId);
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "setText":
                setText(call, result);
                break;
                default:
                    result.notImplemented();
        }
    }

    private void setText(MethodCall methodCall, Result result) {
        String text = (String) methodCall.arguments;
        textView.setText(text);
        result.success(null);
    }

    @Override
    public View getView() {
        return textView;
    }

    @Override
    public void dispose() {

    }
}
