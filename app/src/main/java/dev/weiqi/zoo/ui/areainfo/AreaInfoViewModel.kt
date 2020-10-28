package dev.weiqi.zoo.ui.areainfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.model.MutableStatefulLiveData
import dev.weiqi.zoo.model.State
import dev.weiqi.zoo.model.StatefulLiveData
import dev.weiqi.zoo.repository.RemoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AreaInfoViewModel : ViewModel(), KoinComponent {

    private val repository by inject<RemoteRepository>()

    val areaInfoBeanList: StatefulLiveData<List<AreaInfoBean>> get() = _areaInfoBeanList
    private val _areaInfoBeanList = MutableStatefulLiveData<List<AreaInfoBean>>()

    fun requestAreaInfoBeanList() {
        viewModelScope.launch {
            repository.getAreaInfoList()
                .catch { error ->
                    _areaInfoBeanList.value = State.empty()
                }
                .collect {
                    _areaInfoBeanList.value = State.successOrEmpty(it)
                }
        }
    }
}