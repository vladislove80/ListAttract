package com.group.listattract.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group.listattract.data.repos.Repository
import com.group.listattract.data.repos.RepositoryImpl
import com.group.listattract.view.main.MainViewModel

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = (
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) MainViewModel(
                repository
            )
            else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")) as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance() = INSTANCE ?: synchronized(
            ViewModelFactory::class.java) {
            INSTANCE
                ?: ViewModelFactory(RepositoryImpl.getInstance()).also { INSTANCE = it }
        }
    }
}