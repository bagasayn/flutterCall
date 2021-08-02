import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platformFlutter = const MethodChannel("channel_flutter");
  static const platformAndroid = const MethodChannel("channel_android");
  static const CHANNEL_NAME = "com.example.example/channel";

  @override
  void initState() {
    // TODO: implement initState
    platformAndroid.setMethodCallHandler((call) async =>
        call.method == "call" ? clickFromAndroid() : print("no"));
    listenChannel();
    super.initState();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    platformFlutter.setMethodCallHandler(null);
    platformAndroid.setMethodCallHandler(null);
    super.dispose();
  }

  void _incrementCounter() {
    platformFlutter.invokeMethod("navToScreen");
    print("Push navToScreen from Flutter");
  }

  void listenChannel() {
    ServicesBinding.instance?.defaultBinaryMessenger
        .setMessageHandler(CHANNEL_NAME, (message) async {
      if (message != null) {
        final double x = message.getFloat64(0);
        final int y = message.getInt32(8);
      }
      return null;
    });
  }

  Future<Null> sendDataToNative() async {
    final WriteBuffer buffer = WriteBuffer()
      ..putFloat64(3.1415)
      ..putInt32(123456789);
    final ByteData message = buffer.done();
    await ServicesBinding.instance?.defaultBinaryMessenger
        .send(CHANNEL_NAME, message);
  }

  void clickFromAndroid() {
    sendDataToNative();
    print("Push from Android side");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
