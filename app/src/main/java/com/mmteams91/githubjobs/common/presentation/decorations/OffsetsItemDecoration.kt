package com.fourxxi.mc21.common.ui.decorations

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by mmaruknin on 09.01.19.
 * mc21
 */
class OffsetsItemDecoration(private val getOffsets: (parent: RecyclerView, position: Int) -> Rect?) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val rect = getOffsets.invoke(parent, position)
        if (rect == null) {
            outRect.setEmpty()
        } else outRect.set(rect)
    }
}
