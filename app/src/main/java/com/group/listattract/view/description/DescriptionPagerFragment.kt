package com.group.listattract.view.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.group.listattract.R
import com.group.listattract.model.Item
import kotlinx.android.synthetic.main.fragment_description_pager.*

class DescriptionPagerFragment : Fragment(R.layout.fragment_description_pager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arguments?.getSerializable(ITEMS) as ArrayList<Item>
        val currentPos = arguments?.getInt(INDEX)

        with(pager) {
            pageMargin = 20
            val supportFragmentManager = activity?.supportFragmentManager
            adapter = if (supportFragmentManager != null) {
                DescriptionPagerAdapter(supportFragmentManager, items).apply {
                    offscreenPageLimit = count
                }
            } else null
            if (currentPos != null) currentItem = currentPos
        }
    }

    companion object {
        private const val ITEMS = "saveItemList"
        private const val INDEX = "saveCurrentItemIndex"

        fun newInstance(items: ArrayList<Item>?, index: Int): DescriptionPagerFragment {
            val bundle = Bundle()
            bundle.putSerializable(ITEMS, items)
            bundle.putInt(INDEX, index)
            val fragment = DescriptionPagerFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}