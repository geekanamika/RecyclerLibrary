package com.example.list.recylerviewhelper

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val itemTouchHelperAdapter: ItemTouchHelperAdapter,
    private val isItemSwipeEnabled: Boolean,
    private val isItemRigtEnabled: Boolean,
    private val isLongPressEnabled: Boolean
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags: Int
        if (this.isItemSwipeEnabled && this.isItemRigtEnabled) {
            swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        } else if (this.isItemRigtEnabled) {
            swipeFlags = ItemTouchHelper.START
        } else {
            swipeFlags = ItemTouchHelper.END
        }

        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)

    }

    override fun onMove(recyclerView: RecyclerView, source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // Notify the adapter of the move
        this.itemTouchHelperAdapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        this.itemTouchHelperAdapter.onItemDismiss(viewHolder.adapterPosition, direction)
    }


    override fun canDropOver(recyclerView: RecyclerView, current: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        return current.itemViewType == target.itemViewType
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        //        if (viewHolder instanceof RVHViewHolder) {
        //            // Tell the view holder it's time to restore the idle state
        //            final RVHViewHolder itemViewHolder = (RVHViewHolder) viewHolder;
        //            itemViewHolder.onItemClear();
        //        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        if (ItemTouchHelper.ACTION_STATE_SWIPE == actionState) {
            // Fade out the view as it is swiped out of the parent's bounds
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}
