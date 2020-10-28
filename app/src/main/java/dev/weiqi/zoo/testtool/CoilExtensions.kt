package dev.weiqi.zoo.testtool

import android.widget.ImageView
import coil.ImageLoaderBuilder
import coil.api.load
import coil.request.CachePolicy
import coil.size.ViewSizeResolver

fun ImageView.loadWithLoader(url: String, builder: ImageLoaderBuilder.() -> Unit) {
//    val imageLoader = ImageLoader.Builder(context)
//        .apply(builder)
//        .build()
//
//    val request = LoadRequest.Builder(context)
    load(url) {
        size(ViewSizeResolver(this@loadWithLoader))
        crossfade(true)
        memoryCachePolicy(CachePolicy.DISABLED)
    }
}