package com.example.example

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel

class HomeActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var channelAndroid: MethodChannel
    private var flutterEngine: FlutterEngine? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        flutterEngine = FlutterEngineCache.getInstance().get("flutter")
        channelAndroid =
            MethodChannel(flutterEngine?.dartExecutor?.binaryMessenger, "channel_android")
        button = findViewById(R.id.button)
        button.setOnClickListener {
            channelAndroid.invokeMethod("call",
                null, object : MethodChannel.Result {
                override fun success(result: Any?) {

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