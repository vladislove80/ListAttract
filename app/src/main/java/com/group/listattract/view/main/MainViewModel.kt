package com.group.listattract.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.listattract.data.DataCallback
import com.group.listattract.data.repos.Repository
import com.group.listattract.model.Item

private const val TAG = "MainViewModel"

class MainViewModel(private val repository: Repository) : ViewModel() {
    val itemsLiveData: MutableLiveData<MutableList<Item>?> = MutableLiveData()
    lateinit var dataList: MutableList<Item>
    var isSearchEnable = false

    fun getItem() {
        repository.loadItems(object : DataCallback<Item> {
            override fun onCompleted(data: MutableList<Item>) {
                dataList = data
                itemsLiveData.postValue(data)
            }

            override fun onError(throwable: Throwable) {
                Log.e(TAG, "Throwable ", throwable)
                itemsLiveData.postValue(null)
            }
        })
    }

    fun getCachedItems() = if (::dataList.isInitialized) dataList else mutableListOf()
}