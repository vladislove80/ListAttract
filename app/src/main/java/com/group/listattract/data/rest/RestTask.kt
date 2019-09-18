package com.group.listattract.data.rest

import android.os.Handler
import android.util.Log
import com.group.listattract.data.DataCallback
import com.group.listattract.model.Item
import org.json.JSONObject

private const val TAG = "RestTask"

class RestTask(private val handler: Handler, private val callback: DataCallback<Item>) : Runnable {
    override fun run() {
        try {
            val response = RestClient().createCall().execute()
            val jsonObject = JSONObject(response.body()?.string() ?: "")
            val jsonArray = jsonObject.getJSONArray("result")
            val items = ArrayList<Item>(jsonArray.length())

            for (i in 0 until jsonArray.length()) {
                items.add(with(jsonArray.getJSONObject(i)) {
                    Item(
                        id = getString("id"),
                        albumId = getString("album_id"),
                        title = getString("title"),
                        url = getString("url")
                    )
                }
                )
            }

            handler.post {
                callback.onCompleted(items)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception ", e)
            handler.post {
                callback.onError(e)
            }
        }
    }
}