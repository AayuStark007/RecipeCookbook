package dev.aayushgupta.recipecookbook.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val spaceSize: Int, private val spanCount: Int = 1) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(spaceSize, spaceSize, spaceSize, spaceSize)
    }
}