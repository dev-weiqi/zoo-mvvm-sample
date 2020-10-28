package dev.weiqi.zoo.ui.areadetail.plantinfo

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.clear
import dev.weiqi.zoo.R
import dev.weiqi.zoo.bean.PlantInfoBean
import dev.weiqi.zoo.component.singleClick
import dev.weiqi.zoo.testtool.loadWithLoader
import dev.weiqi.zoo.network.dto.response.PlantInfoRespDto
import dev.weiqi.zoo.network.dto.response.toBean
import dev.weiqi.zoo.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.item_plant_info.view.*

class PlantInfoAdapter : BaseAdapter<PlantInfoBean, BaseAdapter.ViewHolder<PlantInfoBean>>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1

        private val headerBean = PlantInfoRespDto.Result.ResultInfo().toBean()
    }

    var onItemClicked: (PlantInfoBean, ImageView) -> Unit = { _, _ -> }

    override fun getItemViewType(position: Int): Int {
        val isHeader = position == 0
        return if (isHeader) TYPE_HEADER else TYPE_CONTENT
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseAdapter.ViewHolder<PlantInfoBean> {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflateView(R.layout.item_plant_info_header, parent))
            else -> ViewHolder(inflateView(R.layout.item_plant_info, parent))
        }
    }

    override fun submitList(list: List<PlantInfoBean>?) {
        val mutableList = mutableListOf<PlantInfoBean>()
        mutableList.add(0, headerBean)
        super.submitList(list)
    }

    class HeaderViewHolder(view: View) : BaseAdapter.ViewHolder<PlantInfoBean>(view)

    inner class ViewHolder(view: View) : BaseAdapter.ViewHolder<PlantInfoBean>(view) {

        private val imgView: ImageView = itemView.imgView
        private val tvName: TextView = itemView.tvName
        private val tvAlias: TextView = itemView.tvAlias

        override fun bindTo(data: PlantInfoBean) {
            imgView.loadWithLoader(data.imgUrl) { availableMemoryPercentage(0.1) }
            tvName.text = data.name
            tvAlias.text = data.alias

            itemView.singleClick {
                onItemClicked(data, imgView)
            }
        }

        override fun unbindTo() {
            tvName.text = null
            tvAlias.text = null
            imgView.clear()
            itemView.setOnClickListener(null)
        }
    }
}