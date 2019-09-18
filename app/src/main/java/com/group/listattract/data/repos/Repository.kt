package com.group.listattract.data.repos

import com.group.listattract.data.DataCallback
import com.group.listattract.model.Item

interface Repository {
    fun getItems(callback: DataCallback<Item>)
//    fun getItemDescription(callback: DataCallback<ItemDescription>)
}