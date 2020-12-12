package com.cic.formation.mymovies.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


/**
 * Created by Yassine BELDI @Y4583L on 5/6/18.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

/**
 * Toggles view visible state setting View.GONE if not visible
 */
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

val ViewGroup.views: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

fun ImageView.loadImage(url: String? = "") {
    GlideApp.with(context)
            .load("https://image.tmdb.org/t/p/w500/$url")
            .into(this)
}