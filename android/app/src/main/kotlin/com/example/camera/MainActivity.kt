package com.example.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.data
import android.app.Activity
import android.support.v4.app.NotificationCompat.getExtras
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.ByteArrayOutputStream


class MainActivity: FlutterActivity() {
  private lateinit var methodResult: MethodChannel.Result

  private val CHANNEL = "camera.flutter.io/camera"
  private val METHOD_CAMERA= "getCamera"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)

    MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
      when (call.method) {
        METHOD_CAMERA->{
          methodResult=result
          openCameraIntent()
        }
      }
    }
  }

  private val REQUEST_CAPTURE_IMAGE = 100

  private fun openCameraIntent() {
    val pictureIntent = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
    )

    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int,
                                data: Intent?) {
    if (requestCode == REQUEST_CAPTURE_IMAGE) {
      if (data != null && data.extras != null) {
          val bitmap = data.extras!!.get("data") as Bitmap
          val stream = ByteArrayOutputStream()
          bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
          val byteArray = stream.toByteArray()
          methodResult.success(byteArray)
      }
    }
  }


}
