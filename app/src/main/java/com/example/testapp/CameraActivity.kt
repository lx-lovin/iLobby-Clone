package com.example.testapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CameraActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        imageButton.setOnClickListener {

            cameraKitView.captureImage { cameraKitView, bytes ->
                var name = UUID.randomUUID().toString() + ".jpg"
                val savedPhoto = File(Environment.getExternalStorageDirectory(), name)
                val outputStream = FileOutputStream(savedPhoto.path)
                outputStream.write(bytes)
                outputStream.close()


                val sdCard = Environment.getExternalStorageDirectory()

                val directory = File(sdCard.absolutePath)

                val file = File(directory, name) //or any other format supported



                Compressor(this).compressToBitmapAsFlowable(file).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { bitmap ->
                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        var byteArray = baos.toByteArray()

                        var intent: Intent = Intent()
                        intent.putExtra("bytes", byteArray)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
            }
            }


    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }
    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        cameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
