package com.aayush.telewise.util.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.aayush.telewise.R

class DotItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val drawable = ContextCompat.getDrawable(context, R.drawable.dot_separator)!!

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            val position = parent.getChildAdapterPosition(view)
            if (position == RecyclerView.NO_POSITION) return

            outRect.right = if (position == adapter.itemCount - 1) 0 else drawable.intrinsicWidth
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children.forEach { view ->
                val position = parent.getChildAdapterPosition(view)
                if (position == RecyclerView.NO_POSITION) return

                if (position != adapter.itemCount - 1) {
                    val left = view.right + (drawable.intrinsicWidth / 2)
                    val top = parent.paddingTop + (view.height / 2)
                    val right = left + drawable.intrinsicWidth
                    val bottom = top + drawable.intrinsicHeight - parent.paddingBottom
                    drawable.setBounds(left, top, right, bottom)
                    drawable.draw(c)
                }
            }
        }
    }
}
