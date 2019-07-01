package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list.recylerviewhelper.ItemClickListener
import com.example.list.recylerviewhelper.ItemTouchHelperCallback
import com.example.myapplication.data.AppNetworkSource
import com.example.myapplication.data.Restaurant


class MainActivity : AppCompatActivity() {

    private var resLoadedData: LiveData<MutableList<Restaurant>>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myrecyclerview : RecyclerView  = findViewById(R.id.my_list)

        // network call
        val networkSource = AppNetworkSource.getInstance()
        resLoadedData = networkSource.loadedRestaurants
        networkSource.loadRestaurants()


        val myAdapter = PersonAdapter(mutableListOf())

        myrecyclerview.hasFixedSize()
        myrecyclerview.layoutManager = LinearLayoutManager(this)
        myrecyclerview.adapter = myAdapter

        // set touch helper
        val callback = ItemTouchHelperCallback(
            myAdapter,
            isItemSwipeEnabled = true,
            isItemRigtEnabled = true,
            isLongPressEnabled = true
        )

        // observer data change
        resLoadedData?.observe(this, Observer {
                value -> Log.d("myTag", "onobserver " + value.size)

            myAdapter.myDataList?.clear()
            myAdapter.myDataList?.addAll(value)
            myAdapter!!.notifyDataSetChanged()
        })


        val helper = ItemTouchHelper(callback)
         helper.attachToRecyclerView(myrecyclerview)

        myrecyclerview.addOnItemTouchListener(ItemClickListener(
            this, object : ItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val value = "Clicked Item " + myAdapter.myDataList?.get(position) + " at " + position
                    Toast.makeText(this@MainActivity,value, Toast.LENGTH_SHORT ).show()
                }
            }
        ) )


    }
}
