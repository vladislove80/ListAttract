package com.group.listattract.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.group.listattract.R
import com.group.listattract.data.repos.RepositoryImpl
import com.group.listattract.model.Item
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.view.*

class ItemHolder(recyclerView: ViewGroup) :
    RecyclerView.ViewHolder(createView(recyclerView, R.layout.item)),
    View.OnClickListener, LayoutContainer {

    private lateinit var listener: OnItemClickListener<Item>

    private var item: Item? = null

    override val containerView: View = itemView

    init {
        initListeners(intArrayOf(R.id.cvItem))
    }

    private fun initListeners(viewIds: IntArray?) {
        itemView.setOnClickListener(this)
        viewIds?.forEach { itemView.findViewById<View>(it)?.setOnClickListener(this) }
    }

    internal fun bindItem(item: Item) {
        this.item = item
        containerView.tvName.text = item.title
        containerView.tvTime.text = item.time

        RepositoryImpl.getInstance().loadImage(item.url) {
            val ivImage = containerView.ivImage
            ivImage.setImageBitmap(it)
        }
    }

    override fun onClick(v: View) {
        item?.let { model -> if (::listener.isInitialized) listener.onItemViewClick(v, model) }
    }

    fun setOnItemClickListener(listener: OnItemClickListener<Item>) {
        this.listener = listener
    }

    interface OnItemClickListener<T> {
        fun onItemViewClick(view: View, item: T)
    }

    private companion object {
        fun createView(recyclerView: ViewGroup, @LayoutRes id: Int): View {
            return LayoutInflater.from(recyclerView.context).inflate(id, recyclerView, false)
        }
    }
}