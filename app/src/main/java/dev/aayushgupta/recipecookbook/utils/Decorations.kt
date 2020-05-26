package dev.aayushgupta.recipecookbook.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private var space: Int, private var span: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.top = space*2

        if ((parent.getChildLayoutPosition(view)+1) % span == 0) {
            outRect.right = space
        }
    }
}