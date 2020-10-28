package dev.weiqi.zoo.extension

import android.widget.ImageView
import coil.api.load
import coil.request.CachePolicy
import coil.size.ViewSizeResolver

fun ImageView.loadWithLoader(url: String) {
    load(url) {
        size(ViewSizeResolver(this@loadWithLoader))
        crossfade(true)
        memoryCachePolicy(CachePolicy.DISABLED)
    }
}