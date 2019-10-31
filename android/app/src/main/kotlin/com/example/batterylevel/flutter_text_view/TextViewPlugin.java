package com.example.batterylevel.flutter_text_view;

import io.flutter.plugin.common.PluginRegistry;

public class TextViewPlugin {
    public static void registerWith(PluginRegistry registry) {
        final String key = "FlutterTextView";
        if(registry.hasPlugin(key)) return;
        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry()
                .registerViewFactory("com.example.batterylevel/textview",
                        new TextViewFactory(registrar.messenger()));
    }
}
