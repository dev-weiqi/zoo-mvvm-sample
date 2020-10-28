package dev.weiqi.zoo.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DATA, VH : BaseAdapter.ViewHolder<DATA>> :
    ListAdapter<DATA, VH>(DiffItemCallback()) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onViewRecycled(holder: VH) {
        holder.unbindTo()
    }

    protected fun inflateView(@LayoutRes layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    open class ViewHolder<DATA>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bindTo(data: DATA) = Unit
        open fun unbindTo() = Unit
    }
}

class DiffItemCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}