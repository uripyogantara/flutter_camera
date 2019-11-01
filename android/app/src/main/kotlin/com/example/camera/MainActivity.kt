package com.example.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity: FlutterActivity() {
  private lateinit var methodResult: MethodChannel.Result

  private val CHANNEL = "camera.flutter.io/camera"
  private val METHOD_CAMERA= "getCamera"

  private lateinit var imageFilePath:String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)

    MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
      when (call.method) {
        METHOD_CAMERA->{
          methodResult=result
          takePicture()
        }
      }
    }
  }

  private val TAKE_PICTURE = 1

  fun takePicture(){
    val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    if(takePictureIntent.resolveActivity(getPackageManager()) != null){

      var photoFile: File? = null
      try {
        photoFile =createImageFile()
      }catch (e: IOException){
//      Log.d(LOG_TAG,e.printStackTrace().toString())
      }

      if(photoFile !=null) {
//        val photoUri: Uri = FileProvider.getUriForFile(this,"com.example.image_upload.fileprovider",photoFile)
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
        startActivityForResult(takePictureIntent,TAKE_PICTURE)
      }
    }
  }

  @Throws(IOException::class)
  private fun createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(Date())
    val imageFileName = "IMG_" + timeStamp + "_"
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
    )

    imageFilePath = image.absolutePath
    return image
  }

  override fun onActivityResult(requestCode: Int,
                                resultCode: Int,
                                data: Intent) {

    if (requestCode == TAKE_PICTURE) {
      methodResult.success("suksesss fotonya")

    }
  }
}
