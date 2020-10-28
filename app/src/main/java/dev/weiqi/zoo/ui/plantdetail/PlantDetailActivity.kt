package dev.weiqi.zoo.ui.plantdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import dev.weiqi.zoo.R
import dev.weiqi.zoo.bean.PlantDetailBean
import dev.weiqi.zoo.extension.argsOrDefault
import dev.weiqi.zoo.extension.loadWithLoader
import dev.weiqi.zoo.extension.vertical
import dev.weiqi.zoo.common.required
import dev.weiqi.zoo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_plant_detail.*

class PlantDetailActivity : BaseActivity() {

    companion object {

        private const val KEY_PLANT_NAME = "KEY_PLANT_NAME"
        private const val KEY_PLANT_IMAGE_URL = "KEY_PLANT_IMAGE_URL"
        private const val KEY_PLANT_DETAIL_BEAN = "KEY_PLANT_DETAIL_BEAN"

        fun newIntent(
            context: Context,
            name: String,
            imgUrl: String,
            plantDetailBeanList: List<PlantDetailBean>
        ): Intent {
            return Intent(context, PlantDetailActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        KEY_PLANT_NAME to name,
                        KEY_PLANT_IMAGE_URL to imgUrl,
                        KEY_PLANT_DETAIL_BEAN to ArrayList(plantDetailBeanList)
                    )
                )
            }
        }
    }

    private val adapter = PlantDetailAdapter()
    private val name by argsOrDefault<String>(KEY_PLANT_NAME) { required() }
    private val imgUrl by argsOrDefault<String>(KEY_PLANT_IMAGE_URL) { required() }
    private val plantInfoBeanList by argsOrDefault<ArrayList<PlantDetailBean>>(KEY_PLANT_DETAIL_BEAN) { required() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)
        initToolbar(name)
        initView()
    }

    override fun initToolbar(title: String) {
        super.initToolbar(title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        recyclerView.vertical(adapter)
        adapter.submitList(plantInfoBeanList.toList())

        imgView.loadWithLoader(imgUrl)
    }
}