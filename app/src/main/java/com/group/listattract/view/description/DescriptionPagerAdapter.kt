package com.group.listattract.view.description

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.group.listattract.model.Item
import java.util.*

class DescriptionPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val items: ArrayList<Item>
) :
    FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return DescriptionItemFragment.newInstance(items[position])
    }

    override fun getCount(): Int {
        return items.size
    }
}
