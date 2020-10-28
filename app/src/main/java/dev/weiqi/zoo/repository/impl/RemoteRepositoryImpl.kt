package dev.weiqi.zoo.repository.impl

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dev.weiqi.zoo.App
import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.bean.PlantInfoBean
import dev.weiqi.zoo.component.FlowDownloader
import dev.weiqi.zoo.component.Progress
import dev.weiqi.zoo.constant.Const
import dev.weiqi.zoo.network.api.ApiService
import dev.weiqi.zoo.network.dto.response.toBeanList
import dev.weiqi.zoo.repository.RemoteRepository
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class RemoteRepositoryImpl : RemoteRepository, KoinComponent {

    private val api: ApiService by inject()

    override fun getAreaInfoList(): Flow<List<AreaInfoBean>> {
        val cacheFile = File(App.context.cacheDir, "area_info.csv")
        return FlowDownloader().start(
            url = Const.AREA_INFO_URL,
            cacheFile = cacheFile,
            checkCache = true
        )
            .filter { it is Progress.Done }
            .flatMapConcat { progress ->
                val file = progress.file
                flow {
                    val list = csvReader {
                        charset = Const.AREA_INFO_CSV_CHARSET
                    }.readAllWithHeader(file).map {
                        AreaInfoBean(
                            imgUrl = it[AreaInfoBean.KEY_PIC_PATH].orEmpty(),
                            name = it[AreaInfoBean.KEY_TITLE].orEmpty(),
                            desc = it[AreaInfoBean.KEY_SUBTITLE].orEmpty(),
                            openDayInfo = it[AreaInfoBean.KEY_OPEN_DAY_INFO].orEmpty(),
                            openUrl = it[AreaInfoBean.KEY_OPEN_URL].orEmpty()
                        )
                    }
                    emit(list)
                }
            }
    }

    override fun getPlantInfoBeanList(query: String): Flow<List<PlantInfoBean>> {
        return api.getPlantInfo(query).map { it.toBeanList() }
    }
}

