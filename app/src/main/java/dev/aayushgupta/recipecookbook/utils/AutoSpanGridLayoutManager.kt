package dev.aayushgupta.recipecookbook.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class AutoSpanGridLayoutManager constructor(context: Context, private var span: Int): GridLayoutManager(context, 1) {

    private var spanChanged: Boolean = true

    init {
        setSpan(span)
    }

    fun setSpan(newSpan: Int) {
        if (newSpan > 0 && newSpan != span) {
            span = newSpan
            spanChanged = true
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (spanChanged && span > 0) {
            val totalSpace = if (orientation == VERTICAL) {
                width - paddingRight - paddingLeft
            } else {
                height - paddingTop - paddingBottom
            }

            val spanCount = max(1, totalSpace / span)
            setSpanCount(spanCount)
            spanChanged = false
        }
        super.onLayoutChildren(recycler, state)
    }
}