package br.com.jwk.samu.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun ImageView.loadImage(photoUrl: String, circle: Boolean = false) {
    Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
            .load(photoUrl)
            .apply { if (circle) this.apply(RequestOptions.circleCropTransform()) }
            .into(this)
}