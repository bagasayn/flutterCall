package com.example.example


import android.content.Intent
import android.util.Log
import android.widget.Button
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {
    private lateinit var clickButton: Button
    private lateinit var channelFlutter: MethodChannel
    private lateinit var channelAndroid: MethodChannel

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        channelFlutter =
            MethodChannel(getFlutterEngine()?.dartExecutor?.binaryMessenger, "channel_flutter")
        setContentView(R.layout.home_activity)

        channelFlutter.setMethodCallHandler { call, _ ->
            if (call.method == "navToScreen") {
                Log.d("NAV", "Good")
                startActivity(Intent("Navigator"))
            } else {
                Log.d("NAV", "Bad")
            }
        }

        channelAndroid =
            MethodChannel(getFlutterEngine()?.dartExecutor?.binaryMessenger, "channel_android")

        clickButton = findViewById(R.id.button)

        clickButton.setOnClickListener {
            channelAndroid.invokeMethod("call", null, object : MethodChannel.Result {
                override fun success(result: Any?) {
                    Log.d("Results", result.toString())
                }


                override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                    TODO("Not yet implemented")
                }

                override fun notImplemented() {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}



