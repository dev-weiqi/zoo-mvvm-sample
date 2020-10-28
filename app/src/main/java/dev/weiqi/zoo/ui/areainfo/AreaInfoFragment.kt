package dev.weiqi.zoo.ui.areainfo

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.weiqi.zoo.R
import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.extension.observeNonNull
import dev.weiqi.zoo.extension.vertical
import dev.weiqi.zoo.model.State
import dev.weiqi.zoo.ui.areadetail.AreaDetailActivity
import dev.weiqi.zoo.util.ToastUtil
import dev.weiqi.zoo.widget.LoadingDialog
import kotlinx.android.synthetic.main.fragment_area_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AreaInfoFragment : Fragment(R.layout.fragment_area_info) {

    companion object {
        fun newInstance(): AreaInfoFragment {
            return AreaInfoFragment()
        }
    }

    private val adapter = AreaInfoAdapter()
    private val viewModel by viewModel<AreaInfoViewModel>()
    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(requireContext())

        viewModel.run {
            areaInfoBeanList.observeNonNull(viewLifecycleOwner, ::handleAreaInfoBeanList)

            requestAreaInfoBeanList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.onItemClicked = {}
        loadingDialog?.dismiss()
    }

    private fun initView() {
        recyclerView.vertical(adapter)
        adapter.onItemClicked = { bean ->
            context?.let { ctx ->
                val intent = AreaDetailActivity.newIntent(ctx, bean)
                startActivity(intent)
            }
        }
    }

    private fun handleAreaInfoBeanList(state: State<List<AreaInfoBean>>) {
        when (state) {
            is State.Loading -> if (state.show) loadingDialog?.show() else loadingDialog?.hide()
            is State.Empty, is State.Error -> ToastUtil.short(getString(R.string.empty_data))
            is State.Success -> {
                val list = state.data
                val layoutManager =
                    recyclerView.layoutManager as? LinearLayoutManager ?: return

                recyclerView.doOnPreDraw {
                    for (i in 0..layoutManager.findLastVisibleItemPosition())
                        recyclerView.children.forEachIndexed { index, childView ->
                            childView.animation =
                                AnimationUtils.loadAnimation(childView.context, R.anim.anim_item)
                                    ?.apply {
                                        val offset = 100L // 0.1 sec
                                        startOffset = (index + 1) * offset
                                        start()
                                    }
                        }
                }
                adapter.submitList(list)
            }
        }
    }
}