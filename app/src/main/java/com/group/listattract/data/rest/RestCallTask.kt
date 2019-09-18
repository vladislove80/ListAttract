package com.group.listattract.data.rest

import android.os.Handler
import com.group.listattract.data.DataCallback
import com.group.listattract.model.Item
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RestTask(private val handler: Handler, private val callback: DataCallback<Item>) : Runnable {
    override fun run() {
        val link =
            "https://gorest.co.in/public-api/photos?_format=json&access-token=jeB68kcRLw9kUwnTOVFbfNKCQZ_cvuQRprDX"
        val jObj = RestClient.getInstance().createCall(link)
        if (jObj != null) {
            val jsonArray = jObj.getJSONArray("result")
            val items = parseJsonArray(jsonArray)
            handler.post {
                callback.onCompleted(items)
            }
        } else handler.post {
            callback.onError(Throwable("RestClient return null on call"))
        }

    }

    private fun parseJsonArray(jsonArray: JSONArray): ArrayList<Item> {
        val initialCapacity = jsonArray.length()
        return ArrayList<Item>(initialCapacity).apply {
            for (i in 0 until initialCapacity) {
                this.add(mapJSONObjectToItem(jsonArray, i))
            }
        }
    }

    private fun mapJSONObjectToItem(jsonArray: JSONArray, i: Int): Item {
        val jsonObject = jsonArray.getJSONObject(i)
        return Item(
            id = jsonObject.getString("id"),
            albumId = jsonObject.getString("album_id"),
            title = jsonObject.getString("title"),
            url = jsonObject.getString("url"),
            time = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault()).format(
                Date()
            )
        )
    }
}