package com.group.listattract.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.group.listattract.R

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance())
            .get(MainViewModel::class.java)

        viewModel.apply {
            itemLiveData.observe(this@MainActivity, Observer {
                if (it != null) it.forEach { item ->
                    Log.d("MainActivity", "onCreate: ${item.title}, ")
                } else Log.d("MainActivity", "onCreate: it == null")

            })
        }

        viewModel.getItem()
    }
}
