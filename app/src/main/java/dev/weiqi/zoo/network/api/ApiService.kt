package dev.weiqi.zoo.network.api

import dev.weiqi.zoo.network.dto.response.PlantInfoRespDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("https://data.taipei/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    fun getPlantInfo(
        @Query("q") query: String,
        @Query("limit") limit: String = "20",
        @Query("offset") offset: String = "0"
    ): Flow<PlantInfoRespDto>
}