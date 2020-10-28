package dev.weiqi.zoo.network.dto.response

import com.google.gson.annotations.SerializedName
import dev.weiqi.zoo.bean.PlantInfoBean

class PlantInfoRespDto {

    @SerializedName("result")
    val result: Result? = null
        get() = field ?: Result()

    class Result {

        @SerializedName("results")
        val resultList: List<ResultInfo>? = null
            get() = field.orEmpty()

        class ResultInfo {
            @SerializedName("\uFEFFF_Name_Ch")
            val name: String? = null
                get() = field.orEmpty()

            @SerializedName("F_AlsoKnown")
            val alias: String? = null
                get() = field.orEmpty()

            @SerializedName("F_Pic01_URL")
            val imgUrl: String? = null
                get() = field.orEmpty()

            @SerializedName("F_Name_En")
            val enName: String? = null
                get() = field.orEmpty()

            @SerializedName("F_Brief")
            val intro: String? = null
                get() = field.orEmpty()

            @SerializedName("F_Feature")
            val feature: String? = null
                get() = field.orEmpty()

            @SerializedName("F_Function＆Application")
            val function: String? = null
                get() = field.orEmpty()

            @SerializedName("F_Update")
            val lastUpdate: String? = null
                get() = field.orEmpty()
        }
    }
}

fun PlantInfoRespDto.Result.ResultInfo.toBean(): PlantInfoBean {
    return PlantInfoBean(
        imgUrl = imgUrl!!,
        name = name!!,
        alias = alias!!,
        enName = enName!!,
        intro = intro!!,
        feature = feature!!,
        function = function!!,
        lastUpdate = lastUpdate!!
    )
}

fun PlantInfoRespDto.toBeanList(): List<PlantInfoBean> {
    return result!!.resultList!!.map { it.toBean() }
}