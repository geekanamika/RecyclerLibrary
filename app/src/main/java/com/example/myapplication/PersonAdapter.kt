package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.list.recylerviewhelper.ItemTouchHelperAdapter
import com.example.list.recylerviewhelper.ItemViewHolder
import com.example.myapplication.data.Restaurant
import java.util.*


class PersonAdapter(dataList: MutableList<Restaurant>?) : RecyclerView.Adapter<PersonAdapter.MyItemViewHolder>() ,
    ItemTouchHelperAdapter {

    var myDataList : MutableList<Restaurant>? = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)

        return MyItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {

        holder.itemHeading.text = myDataList?.get(position)?.name
        holder.itemSubheading.text = myDataList?.get(position)?.cuisines
        holder.itemAddress.text = myDataList?.get(position)?.address

    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean{
        swap(fromPosition, toPosition)
        return false
    }

    override fun onItemDismiss(position: Int, direction: Int) {
        remove(position)
    }


    inner class MyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemViewHolder {

        val itemHeading: TextView = itemView.findViewById(R.id.tv_res_title)
        val itemSubheading : TextView = itemView.findViewById(R.id.tv_cuisines)
        val itemAddress : TextView = itemView.findViewById(R.id.tv_address)
       // val itemDp : ImageView = itemView.findViewById(R.id.item_dp)

        override fun onItemClear() {
            Log.d("myTag","Item is unselected")
        }

        override fun onItemSelected(actionstate: Int) {
            Log.d("myTag","Item is selected")
        }
    }

    private fun remove(position: Int) {
        myDataList?.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun swap(firstPosition: Int, secondPosition: Int) {
        Collections.swap(myDataList, firstPosition, secondPosition)
        notifyItemMoved(firstPosition, secondPosition)
    }
}
