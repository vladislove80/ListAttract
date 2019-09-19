package com.group.listattract.view.description

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.listattract.data.DataCallback
import com.group.listattract.data.repos.Repository
import com.group.listattract.model.Item

class DescriptionViewModel(private val repository: Repository) : ViewModel() {
    val itemsLiveData: MutableLiveData<MutableList<Item>?> = MutableLiveData()

    // should call loadItemDescription() when available
    fun getItem() {
        repository.loadItems(object : DataCallback<Item> {
            override fun onCompleted(data: MutableList<Item>) {
                itemsLiveData.postValue(data)
            }

            override fun onError(throwable: Throwable) {
                Log.e("DescriptionViewModel", "Throwable ", throwable)
                itemsLiveData.postValue(null)
            }
        })
    }
}