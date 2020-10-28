package dev.weiqi.zoo.repository

import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.bean.PlantInfoBean
import dev.weiqi.zoo.component.Progress
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteRepository {
    fun getAreaInfoList():  Flow<List<AreaInfoBean>>
    fun getPlantInfoBeanList(query: String): Flow<List<PlantInfoBean>>
}

