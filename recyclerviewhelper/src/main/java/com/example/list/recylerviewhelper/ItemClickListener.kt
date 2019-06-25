package com.example.list.recylerviewhelper

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener


class ItemClickListener(context: Context, private val mListener: OnItemClickListener?) : OnItemTouchListener {


    private val mGestureDetector: GestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }
    })

    /**
     * The interface On item click listener.
     */
    interface OnItemClickListener {


        fun onItemClick(view: View, position: Int)
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (null != childView && null != mListener && this.mGestureDetector.onTouchEvent(e)) {
            this.mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // Do nothings
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {
        // Do nothing
    }
}
