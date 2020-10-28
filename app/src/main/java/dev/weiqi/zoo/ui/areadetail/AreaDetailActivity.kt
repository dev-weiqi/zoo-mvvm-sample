package dev.weiqi.zoo.ui.areadetail

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import dev.weiqi.zoo.R
import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.bean.PlantDetailBean
import dev.weiqi.zoo.bean.PlantInfoBean
import dev.weiqi.zoo.component.Launcher
import dev.weiqi.zoo.component.singleClick
import dev.weiqi.zoo.testtool.argsOrDefault
import dev.weiqi.zoo.testtool.loadWithLoader
import dev.weiqi.zoo.testtool.observeNonNull
import dev.weiqi.zoo.testtool.vertical
import dev.weiqi.zoo.model.State
import dev.weiqi.zoo.required
import dev.weiqi.zoo.ui.areadetail.plantinfo.PlantInfoAdapter
import dev.weiqi.zoo.ui.base.BaseActivity
import dev.weiqi.zoo.ui.plantdetail.PlantDetailActivity
import dev.weiqi.zoo.util.ToastUtil
import dev.weiqi.zoo.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_area_detail.*
import kotlinx.android.synthetic.main.item_area_info.imgView
import org.koin.androidx.viewmodel.ext.android.viewModel

class AreaDetailActivity : BaseActivity() {

    companion object {

        private const val KEY_AREA_INFO_BEAN = "KEY_AREA_INFO_BEAN"

        fun newIntent(context: Context, areaInfoBean: AreaInfoBean): Intent {
            return Intent(context, AreaDetailActivity::class.java).apply {
                putExtras(bundleOf(KEY_AREA_INFO_BEAN to areaInfoBean))
            }
        }
    }

    private val adapter = PlantInfoAdapter()
    private val viewModel by viewModel<AreaDetailViewModel>()
    private val areaInfoBean by argsOrDefault<AreaInfoBean>(KEY_AREA_INFO_BEAN) { required() }
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_detail)

        initToolbar(title = areaInfoBean.name)
        initView()
        initViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initToolbar(title: String) {
        super.initToolbar(title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initView() {
        loadingDialog = LoadingDialog(this)

        imgView.loadWithLoader(areaInfoBean.imgUrl) {
            availableMemoryPercentage(0.1)
        }

        tvDesc.text = areaInfoBean.desc
        tvLink.singleClick {
            Launcher.openBrowser(this, areaInfoBean.openUrl)
        }

        recyclerView.vertical(adapter).run {
            addItemDecoration(
                DividerItemDecoration(
                    this@AreaDetailActivity,
                    DividerItemDecoration.VERTICAL
                ).apply {
                    setDrawable(ColorDrawable())
                }
            )
        }
        adapter.onItemClicked = ::handleItemClicked
    }

    private fun handleItemClicked(
        bean: PlantInfoBean,
        imgView: ImageView
    ) {
        val bundle = ActivityOptions.makeSceneTransitionAnimation(
            this,
            imgView,
            getString(R.string.image_transition_name)
        ).toBundle()

        val intent =
            PlantDetailActivity.newIntent(
                context = this,
                name = bean.name,
                imgUrl = bean.imgUrl,
                plantDetailBeanList = listOf(
                    PlantDetailBean(title = bean.name, desc = bean.enName),
                    PlantDetailBean(title = getString(R.string.alias), desc = bean.alias),
                    PlantDetailBean(title = getString(R.string.intro), desc = bean.intro),
                    PlantDetailBean(title = getString(R.string.feature), desc = bean.feature),
                    PlantDetailBean(title = getString(R.string.function), desc = bean.function),
                    PlantDetailBean(title = getString(R.string.last_update), desc = bean.lastUpdate)
                )
            )
        startActivity(intent, bundle)
    }

    private fun initViewModel() {
        val lifecycleOwner = this
        viewModel.run {
//            isLoading.observeNonNull(lifecycleOwner) { if (it) loadingDialog?.show() else loadingDialog?.dismiss() }
            plantInfoBeanList.observeNonNull(lifecycleOwner) { state ->
                when (state) {
                    is State.Empty, is State.Error -> ToastUtil.short(getString(R.string.empty_data))
                    is State.Success -> adapter.submitList(state.data)
                }
            }

            requestPlantInfoBeanList(areaInfoBean.name)
        }
    }
}