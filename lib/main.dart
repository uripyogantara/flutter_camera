import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
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
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel("camera.flutter.io/camera");
  Uint8List _image;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body:
          _formUpload(), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  Widget _formUpload() {
    return SingleChildScrollView(
      scrollDirection: Axis.vertical,
      child: Column(
        children: <Widget>[
          Container(
            child: _image == null
                ? Text('No Images Selected')
                : Image.memory(_image),
          ),
          SizedBox(
            height: 50.0,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              RaisedButton(
                child: Icon(Icons.camera),
                onPressed: () async {
                  await platform
                      .invokeMethod("getCamera")
                      .then((stringResponse) {
                    setState(() {
                      _image = stringResponse;
                    });
                    // print(input);
                    // print("response camera $stringResponse");
                    // setState(() {
                    //   _image = File.fromRawPath(input);
                    // });
                    // Map map = json.decode(stringResponse);
                    // print(map);
                  });
                },
              )
            ],
          )
        ],
      ),
    );
  }
}
