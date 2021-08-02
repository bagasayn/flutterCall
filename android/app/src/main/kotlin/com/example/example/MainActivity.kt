package com.example.example


import android.content.Intent
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.nio.ByteBuffer
import java.nio.ByteOrder


class MainActivity : FlutterActivity() {

    private lateinit var channelFlutter: MethodChannel

    @Suppress("PrivatePropertyName")
    private val CHANNEL = "com.example.example/channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        setContentView(R.layout.home_activity)
        FlutterEngineCache.getInstance().put("flutter",flutterEngine)
        channelFlutter =
            MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "channel_flutter")
        channelFlutter.setMethodCallHandler { call, _ ->
            if (call.method == "navToScreen") {
                startActivity(Intent("Navigator"))
            }
        }

        flutterEngine.dartExecutor.binaryMessenger.setMessageHandler(CHANNEL) { message, reply ->
            message?.order(ByteOrder.nativeOrder())

            val x: Double = message?.double ?: 0.0
            val y: Int = message?.int ?: 0

            Log.i("flutter", "Received $x and $y")

            val message2 = ByteBuffer.allocateDirect(12)
            message2.putDouble(x)
            message2.putInt(y)

            flutterEngine.dartExecutor.binaryMessenger.send(CHANNEL, message2) {
                Log.i("flutter", "Sent $x and $y")
            }
            reply.reply(null)
        }
    }

    override fun onDestroy() {
        channelFlutter.setMethodCallHandler(null)
        super.onDestroy()
    }
}



