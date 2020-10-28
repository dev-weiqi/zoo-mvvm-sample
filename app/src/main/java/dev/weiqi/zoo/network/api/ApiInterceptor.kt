package dev.weiqi.zoo.network.api

import dev.weiqi.zoo.component.logD
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject

class ApiInterceptor : Interceptor {

    companion object {
        private const val TAG = "apilog"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val method = request.method()
        val url = request.url()
        val requestBody = request.bodyToString()?.jsonPretty().toString()
        logD(TAG, "--> $method $url\n$requestBody")

        val startTimeMillis = System.currentTimeMillis()
        return chain.proceed(request).also { response ->
            val statusCode = response.code()
            val statusMsg = response.message().orEmpty()
            val tookMs = System.currentTimeMillis() - startTimeMillis
            val responseBody = response.peekBody(4096).string().jsonPretty()
            logD(TAG, "<-- $statusCode $statusMsg $url (${tookMs}ms) \n$responseBody")
        }
    }

    private fun Request.bodyToString(): String? {
        return try {
            val copy = newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: Exception) {
            null
        }
    }

    private fun String.jsonPretty(): String {
        return try {
            val jsonObj = JSONObject(this)
            jsonObj.toString(4)
        } catch (e: Exception) {
            "[Json parse failed]:$e\n$this"
        }
    }
}