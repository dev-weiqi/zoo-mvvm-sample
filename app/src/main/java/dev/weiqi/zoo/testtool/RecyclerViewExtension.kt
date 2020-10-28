package dev.weiqi.zoo.testtool

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.vertical(
    adapter: RecyclerView.Adapter<*>
): RecyclerView {
    return apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.adapter = adapter
    }
}