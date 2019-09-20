package com.group.listattract.view.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.group.listattract.R
import com.group.listattract.model.Item
import com.group.listattract.view.BaseActivity
import com.group.listattract.view.ViewModelFactory
import com.group.listattract.view.main.ITEM_POSITION
import kotlinx.android.synthetic.main.activity_description.*
import java.io.Serializable

class DescriptionActivity : BaseActivity(R.layout.activity_description) {
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

    private fun showPagerFragment(list: ArrayList<Item>, actualItem: Serializable?) {
        DescriptionPagerFragment.newInstance(list, list.indexOf(actualItem)).apply {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fContainer, this, this.javaClass.simpleName)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }
}
