package com.group.listattract.view.description

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.group.listattract.R
import com.group.listattract.model.Item
import com.group.listattract.view.ViewModelFactory
import com.group.listattract.view.main.ITEM_POSITION
import kotlinx.android.synthetic.main.activity_description.*

import java.io.Serializable

class DescriptionActivity : AppCompatActivity(R.layout.activity_description) {
    private lateinit var viewModel: DescriptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actualItem = intent.getSerializableExtra(ITEM_POSITION)
        switchProgressBar(true)
        initViewModel(actualItem)
    }

    private fun initViewModel(actualItem: Serializable?) {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance())
            .get(DescriptionViewModel::class.java)
            .apply {
                itemsLiveData.observe(this@DescriptionActivity, Observer {
                    switchProgressBar(false)
                    if (it != null) {
                        val list = ArrayList<Item>()
                        list.addAll(it)
                        showPagerFragment(list, actualItem)
                    } else tvNoData.visibility = View.VISIBLE
                })
            }.also {
                it.getItem()
            }
    }

    private fun showPagerFragment(
        list: ArrayList<Item>,
        actualItem: Serializable?
    ) {
        val newFragment = DescriptionPagerFragment.newInstance(list, list.indexOf(actualItem))
        supportFragmentManager.beginTransaction()
            .replace(R.id.fContainer, newFragment, newFragment.javaClass.simpleName)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun switchProgressBar(flag: Boolean) {
        pbDescription.visibility = if (flag) View.VISIBLE else View.GONE
    }
}
