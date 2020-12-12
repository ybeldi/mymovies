package com.cic.formation.mymovies.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cic.formation.mymovies.R

/*
 *
 * DividerItemDecoration
 * My movies
 *
 * Created by Yassine BELDI @Y4583L on 3/2/20.
 * Copyright © 2020 IBM. All rights reserved.
 *
 * This file may not be copied and/or distributed without the express
 * permission of IBM.
 *
 */

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val divider: Drawable? = ContextCompat.getDrawable(context, R.drawable.line_divider)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider?.intrinsicHeight!!

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}