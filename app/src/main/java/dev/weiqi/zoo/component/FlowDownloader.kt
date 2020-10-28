package dev.weiqi.zoo.component

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

class FlowDownloader(
    private val okHttpClient: OkHttpClient = OkHttpClient(),
    private val progressPeriodBytes: Long = 10000,
    private val internalBufferBytes: Long = 2048
) {

    fun start(url: String, cacheFile: File, checkCache: Boolean = false): Flow<Progress> {
        return callbackFlow {
            val isCanceled = AtomicBoolean(false)

            if (checkCache && cacheFile.exists()) {
                offer(Progress.Done(file = cacheFile, contentLength = cacheFile.length()))
                awaitClose { isCanceled.set(true) }
                return@callbackFlow
            }

            val request: Request = Request.Builder().url(url).get().build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()

            if (!response.isSuccessful) {
                throw IOException(response.message())
            }

            val sink = cacheFile.sink().buffer()
            val body = response.body()

            if (body != null) {
                val contentLength = body.contentLength()
                var totalBytes = 0L
                var readBytes = 0L
                var progressLimitBytes = 0L

                offer(
                    Progress.Downloading(
                        file = cacheFile,
                        bytesDownloaded = totalBytes,
                        contentLength = contentLength
                    )
                )

                while (readBytes != -1L) {
                    if (isCanceled.get()) {
                        body.close()
                        sink.close()
                        awaitClose { isCanceled.set(true) }
                        return@callbackFlow
                    }

                    readBytes = body.source().read(sink.buffer, internalBufferBytes)
                    totalBytes += readBytes
                    if (totalBytes > progressLimitBytes) {
                        progressLimitBytes += progressPeriodBytes
                        offer(
                            Progress.Downloading(
                                file = cacheFile,
                                bytesDownloaded = totalBytes,
                                contentLength = contentLength
                            )
                        )
                    }
                }

                body.close()
                sink.close()
            }

            offer(Progress.Done(cacheFile, body?.contentLength() ?: 0))
            awaitClose { isCanceled.set(true) }
        }.flowOn(Dispatchers.IO)
    }
}

sealed class Progress(open val file: File) {

    data class Downloading(
        override val file: File,
        val bytesDownloaded: Long,
        val contentLength: Long
    ) : Progress(file)

    data class Done(
        override val file: File,
        val contentLength: Long
    ) : Progress(file)
}