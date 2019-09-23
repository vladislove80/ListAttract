package com.group.listattract.view.description

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.group.listattract.R
import com.group.listattract.data.repos.RepositoryImpl
import com.group.listattract.model.Item
import kotlinx.android.synthetic.main.fragment_item_description.*
import kotlinx.android.synthetic.main.item.ivImage
import kotlinx.android.synthetic.main.item.tvName
import kotlinx.android.synthetic.main.item.tvTime

class DescriptionItemFragment : Fragment(R.layout.fragment_item_description) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getSerializable(ITEM) as Item

        tvName.text = item.title
        tvTime.text = item.time
        tvDesc.text = item.desc

        RepositoryImpl.getInstance().loadImage(item.url) {
            pbImage?.visibility = GONE
            if (it != null) ivImage?.setImageBitmap(it)
            else setPlaceHolder(ivImage)
        }
    }

    private fun setPlaceHolder(ivImage: ImageView) {
        ivImage.setImageDrawable(
            ContextCompat.getDrawable(
                ivImage.context,
                R.drawable.ic_cloud_off_black_24dp
            )
        )
    }

    companion object {

        const val ITEM = "item"

        fun newInstance(item: Item): DescriptionItemFragment {
            val bundle = Bundle()
            bundle.putSerializable(ITEM, item)
            val fragment = DescriptionItemFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}