package dev.weiqi.zoo.network.api

import dev.weiqi.zoo.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

object ApiClient {

    private const val BASE_URL = "https://www.google.com/"
    private const val TIME_OUT_MILLISECONDS = 10_000L

    fun <T : Any> createRetrofitService(kClass: KClass<T>): T {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(ApiInterceptor())
                }
            }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
            .create(kClass.java)
    }
}