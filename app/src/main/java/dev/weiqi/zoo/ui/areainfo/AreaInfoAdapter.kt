package dev.weiqi.zoo.ui.areainfo

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.clear
import dev.weiqi.zoo.R
import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.component.singleClick
import dev.weiqi.zoo.testtool.loadWithLoader
import dev.weiqi.zoo.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.item_area_info.view.*

class AreaInfoAdapter : BaseAdapter<AreaInfoBean, AreaInfoAdapter.ViewHolder>() {

    var onItemClicked: (bean: AreaInfoBean) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflateView(R.layout.item_area_info, parent))
    }

    inner class ViewHolder(private val view: View) : BaseAdapter.ViewHolder<AreaInfoBean>(view) {

        private val imgView: ImageView = itemView.imgView
        private val tvTitle: TextView = itemView.tvTitle
        private val tvSubtitle: TextView = itemView.tvSubtitle
        private val tvOpenDayInfo: TextView = itemView.tvOpenDayInfo

        override fun bindTo(data: AreaInfoBean) {
            imgView.loadWithLoader(data.imgUrl) {
                availableMemoryPercentage(0.1)
            }

            tvTitle.text = data.name
            tvSubtitle.text = data.desc
            tvOpenDayInfo.text = data.openDayInfo.ifEmpty {
                view.context.getString(R.string.empty_data)
            }

            itemView.singleClick { onItemClicked(data) }
        }

        override fun unbindTo() {
            tvTitle.text = null
            tvSubtitle.text = null
            tvOpenDayInfo.text = null
            imgView.clear()
            itemView.setOnClickListener(null)
        }
    }
}