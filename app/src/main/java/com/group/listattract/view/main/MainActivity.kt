package com.group.listattract.view.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.group.listattract.R
import com.group.listattract.model.Item
import com.group.listattract.view.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance()
        )
            .get(MainViewModel::class.java)

        viewModel.apply {
            itemLiveData.observe(this@MainActivity, Observer {
                if (it != null) it.forEach { item ->
                    Log.d("MainActivity", "onCreate: ${item.title}, ")
                    (rvList.adapter as ItemAdapter).addNewItems(it)
                } else Log.d("MainActivity", "onCreate: it == null")
            })
        }

        viewModel.getItem()
        intRecycler()
    }

    private fun intRecycler() {
        rvList.apply {
            val itemAdapter = ItemAdapter(object : ItemHolder.OnItemClickListener<Item> {
                override fun onItemViewClick(view: View, item: Item) {
                    Log.d("MainActivity", "onItemViewClick: ${item.title}, ")
                    Toast.makeText(this@MainActivity, item.id, Toast.LENGTH_SHORT).show()
                }
            })
            adapter = itemAdapter
        }
    }
}
