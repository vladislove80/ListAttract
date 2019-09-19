package com.group.listattract.view.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.group.listattract.R
import com.group.listattract.model.Item
import com.group.listattract.view.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main), Toolbar.OnMenuItemClickListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSearchView()
        initViewModel()
        intRecycler()
    }

    private fun setSearchView() {
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener(this)
        searchView = toolbar.menu.findItem(R.id.search).actionView as SearchView
        searchView.maxWidth = android.R.attr.width
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)

        (searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText).apply {
            hint = resources.getString(R.string.search)
            setHintTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
        }

        val closeBtn = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeBtn.setImageResource(R.drawable.ic_search_close)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance())
            .get(MainViewModel::class.java)
            .apply {
                itemLiveData.observe(this@MainActivity, Observer {
                    if (it != null) (rvList.adapter as ItemAdapter).addItems(it)
                    else tvNoData.visibility = VISIBLE
                    switchProgressBar(false)
                })
            }.also {
                it.getItem()
            }
    }

    private fun intRecycler() {
        rvList.adapter = ItemAdapter(object : ItemHolder.OnItemClickListener<Item> {
            override fun onItemViewClick(view: View, item: Item) {
                Log.d("MainActivity", "onItemViewClick: ${item.title}, ")
                Toast.makeText(this@MainActivity, item.id, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun switchProgressBar(flag: Boolean) {
        progressBar.visibility = if (flag) VISIBLE else GONE
    }

    private fun hideSoftKeyboard(view: View?) {
        view?.postDelayed({
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }, 50L)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        hideSoftKeyboard(searchView)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) (rvList.adapter as ItemAdapter).search(newText)
        return true
    }

    override fun onClose(): Boolean {
        (rvList.adapter as ItemAdapter).addNewItems(viewModel.getCachedItems())
        return false
    }
}
