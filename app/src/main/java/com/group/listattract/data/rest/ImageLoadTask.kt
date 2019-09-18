package com.group.listattract.data.rest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Log
import java.net.URL

private const val TAG = "ImageLoadTask"

class ImageLoadTask(private val uiHandler: Handler, private val url: String, private val onLoad: (Bitmap?) -> Unit) : Runnable {
    override fun run() {
        val mIcon11: Bitmap? = try {
            val imgStream = URL(url).openStream()
            BitmapFactory.decodeStream(imgStream)
        } catch (e: Exception) {
            Log.e(TAG, "Exception ", e)
            null
        }
        uiHandler.post{
            onLoad.invoke(mIcon11)
        }
    }
}