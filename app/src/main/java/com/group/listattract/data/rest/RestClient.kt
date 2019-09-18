package com.group.listattract.data.rest

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class RestClient {
    fun createCall(link: String): JSONObject? {
        val urlConnection = URL(link).openConnection() as HttpURLConnection
        return try {
            JSONObject(getJSONStringFrom(urlConnection))
        } catch (e: JSONException) {
            Log.e("JSON Parser", "Error parsing data $e")
            null
        } catch (ex: IOException) {
            Log.e("RestClient", "Exception ", ex)
            null
        } finally {
            urlConnection.disconnect()
        }
    }

    fun getStreamFrom(url: String): InputStream = URL(url).openStream()

    private fun getJSONStringFrom(urlConnection: HttpURLConnection): String {
        val result = StringBuilder()

        with(urlConnection) {
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "GET"
            connectTimeout = 10000
            readTimeout = 10000
            connect()

            BufferedReader(InputStreamReader(BufferedInputStream(this.inputStream))).apply {
                var line = this.readLine()
                while (line != null) {
                    result.append(line)
                    line = this.readLine()
                }
            }
        }
        return result.toString()
    }

    companion object {
        private var INSTANCE: RestClient? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: synchronized(RestClient::class.java) {
            INSTANCE ?: RestClient().also {
                INSTANCE = it
            }
        }
    }
}