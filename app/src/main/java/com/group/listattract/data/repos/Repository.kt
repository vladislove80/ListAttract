package com.group.listattract.data.repos

import com.group.listattract.data.DataCallback
import com.group.listattract.model.Item

interface Repository {
    fun loadItems(callback: DataCallback<Item>)
//    fun loadItemDescription(callback: DataCallback<ItemDescription>)
}