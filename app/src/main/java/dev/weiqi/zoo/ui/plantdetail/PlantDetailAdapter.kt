package dev.weiqi.zoo.ui.plantdetail

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.weiqi.zoo.R
import dev.weiqi.zoo.bean.PlantDetailBean
import dev.weiqi.zoo.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.item_area_info.view.tvTitle
import kotlinx.android.synthetic.main.item_plant_detial.view.*

class PlantDetailAdapter : BaseAdapter<PlantDetailBean, PlantDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflateView(R.layout.item_plant_detial, parent))
    }

    class ViewHolder(view: View) : BaseAdapter.ViewHolder<PlantDetailBean>(view) {

        private val tvTitle: TextView = itemView.tvTitle
        private val tvDesc: TextView = itemView.tvDesc

        override fun bindTo(data: PlantDetailBean) {
            tvTitle.text = data.title
            tvDesc.text = data.desc
        }

        override fun unbindTo() {
            tvTitle.text = null
            tvDesc.text = null
        }
    }
}