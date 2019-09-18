package com.group.listattract.view.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group.listattract.model.Item

class ItemAdapter(private val listener: ItemHolder.OnItemClickListener<Item>) :
    RecyclerView.Adapter<ItemHolder>() {
    private val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(parent).apply {
            setOnItemClickListener(listener)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindItem(items[position])
    }

    fun addNewItems(newItems: MutableList<Item>) {
        val isAdded = items.addAll(newItems)
        if (isAdded) notifyDataSetChanged()
    }
}