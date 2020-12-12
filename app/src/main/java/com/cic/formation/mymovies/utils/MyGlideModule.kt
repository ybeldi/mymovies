package com.cic.formation.mymovies.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setMemoryCache(LruResourceCache((10 * 1024 * 1024).toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    }
}