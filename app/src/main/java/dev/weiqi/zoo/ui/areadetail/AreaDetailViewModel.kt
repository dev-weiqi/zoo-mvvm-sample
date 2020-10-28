package dev.weiqi.zoo.ui.areadetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weiqi.zoo.bean.PlantInfoBean
import dev.weiqi.zoo.model.MutableStatefulLiveData
import dev.weiqi.zoo.model.State
import dev.weiqi.zoo.model.StatefulLiveData
import dev.weiqi.zoo.repository.RemoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AreaDetailViewModel : ViewModel(), KoinComponent {

    private val repository by inject<RemoteRepository>()

    val plantInfoBeanList: StatefulLiveData<List<PlantInfoBean>> get() = _plantInfoBeanList
    private val _plantInfoBeanList = MutableStatefulLiveData<List<PlantInfoBean>>()

    fun requestPlantInfoBeanList(query: String) {
        viewModelScope.launch {
            repository.getPlantInfoBeanList(query)
                .onStart { _plantInfoBeanList.value = State.loading(true) }
                .onEach { _plantInfoBeanList.value = State.loading(false) }
                .catch { error -> _plantInfoBeanList.value = State.error(error) }
                .collect { _plantInfoBeanList.value = State.success(it) }
        }
    }
}