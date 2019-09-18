package com.group.listattract.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.listattract.data.DataCallback
import com.group.listattract.model.Item
import com.group.listattract.data.repos.Repository

private const val TAG = "MainViewModel"
class MainViewModel(private val repository: Repository) : ViewModel() {
    val itemLiveData: MutableLiveData<MutableList<Item>?> = MutableLiveData()

    fun getItem() {
        repository.getItems(object : DataCallback<Item> {
            override fun onCompleted(data: MutableList<Item>) {
                itemLiveData.postValue(data)
            }

            override fun onError(throwable: Throwable) {
                Log.e(TAG, "Throwable ", throwable)
                itemLiveData.postValue(null)
            }
        })
    }
}