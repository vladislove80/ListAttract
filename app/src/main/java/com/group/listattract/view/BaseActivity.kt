package com.group.listattract.view

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    fun switchProgressBar(flag: Boolean) {
        progressBar?.visibility = if (flag) View.VISIBLE else View.GONE
    }

    fun isTablet(): Boolean {
        return resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun hideSoftKeyboard(view: View?) {
        view?.postDelayed({
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }, 50L)
    }
}