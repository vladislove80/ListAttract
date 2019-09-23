package com.group.listattract.view.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.group.listattract.R
import com.group.listattract.model.Item
import com.group.listattract.view.BaseActivity
import com.group.listattract.view.ViewModelFactory
import com.group.listattract.view.description.DescriptionActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*

const val ITEM_POSITION = "position"

class MainActivity : BaseActivity(R.layout.activity_main), Toolbar.OnMenuItemClickListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener,
    NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (!isTablet()) drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDrawer()
        setToolbarWithSearchView()
        initViewModel()
        intRecycler()
    }

    private fun setDrawer() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        (findViewById<View>(R.id.navigation_view) as NavigationView)
            .setNavigationItemSelectedListener(this)
    }

    private fun setToolbarWithSearchView() {
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener(this)

        if (isTablet()) {
            toolbar.getChildAt(0).setPadding( //set padding for toolbar title view
                resources.getDimension(R.dimen.main_content_padding_left).toInt(),
                0,
                0,
                0
            )
        }

        setSearchView()
    }

    private fun setSearchView() {
        searchView = toolbar.menu.findItem(R.id.search).actionView as SearchView
        with(searchView) {
            maxWidth = android.R.attr.width
            setOnQueryTextListener(this@MainActivity)
            setOnCloseListener(this@MainActivity)
            if (isTablet()) setPadding(//set padding for toolbar search view
                resources.getDimension(R.dimen.main_content_padding_left).toInt(),
                0,
                0,
                0
            )

            setSearchEditText()
            findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
                .setImageResource(R.drawable.ic_search_close)
        }
    }

    private fun SearchView.setSearchEditText() =
        (findViewById<EditText>(androidx.appcompat.R.id.search_src_text)).apply {
            hint = resources.getString(R.string.search)
            val color = ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
            setHintTextColor(color)
            setTextColor(color)
        }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance())
            .get(MainViewModel::class.java)
            .apply {
                itemsLiveData.observe(this@MainActivity, Observer {
                    if (it != null) (rvList.adapter as ItemAdapter).addNewItems(it)
                    else tvNoData.visibility = VISIBLE
                    switchProgressBar(false)
                })
            }.also {
                if (!it.isSearchEnable) it.getItem()
            }
    }

    private fun intRecycler() {
        rvList.adapter = ItemAdapter(object : ItemHolder.OnItemClickListener<Item> {
            override fun onItemViewClick(view: View, item: Item) {
                val intent = Intent(this@MainActivity, DescriptionActivity::class.java)
                intent.putExtra(ITEM_POSITION, item)
                startActivity(intent)
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (::searchView.isInitialized) searchView.clearFocus()
        hideSoftKeyboard(searchView)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            (rvList?.adapter as ItemAdapter).search(it)
            viewModel.isSearchEnable = true
        }
        return true
    }

    override fun onClose(): Boolean {
        with(viewModel) {
            (rvList.adapter as ItemAdapter).addNewItems(this.getCachedItems())
            this.isSearchEnable = false
        }
        return false
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START) && !isTablet()) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
