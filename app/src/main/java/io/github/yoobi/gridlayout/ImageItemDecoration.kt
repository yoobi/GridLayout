package io.github.yoobi.gridlayout

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView




class ImageItemDecoration(private val padding: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val totalSpanCount = getTotalSpanCount(parent)
        val spanSize = getItemSpanSize(parent, position)

        outRect.top = padding
        outRect.left = if (isFirstInRow(position, totalSpanCount, spanSize)) padding else padding / 2
        outRect.right = if (isLastInRow(position, totalSpanCount, spanSize)) padding else padding / 2
    }

    private fun isFirstInRow(position: Int, totalSpanCount: Int, spanSize: Int): Boolean {
        return if (totalSpanCount != spanSize) {
            position % totalSpanCount == 0
        } else {
            true
        }
    }

    private fun isLastInRow(position: Int, totalSpanCount: Int, spanSize: Int): Boolean =
        isFirstInRow(position + 1, totalSpanCount, spanSize)

    private fun getTotalSpanCount(parent: RecyclerView): Int =
        (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1

    private fun getItemSpanSize(parent: RecyclerView, position: Int): Int =
        (parent.layoutManager as? GridLayoutManager)?.spanSizeLookup?.getSpanSize(position) ?: 1
}