package com.group.listattract.model

import java.io.Serializable

data class Item(
    val id: String,
    val desc: String,
    val title: String,
    val url: String,
    val time: String
) : Serializable