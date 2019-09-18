package com.group.listattract.data.rest

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

class RestClient {
    fun createCall(): Call = OkHttpClient().newCall(
            Request.Builder()
                .url("https://gorest.co.in/public-api/photos?_format=json&access-token=jeB68kcRLw9kUwnTOVFbfNKCQZ_cvuQRprDX")/*"http://test.php-cd.attractgroup.com/test.json"*/
                .get()
                .build()
        )
}