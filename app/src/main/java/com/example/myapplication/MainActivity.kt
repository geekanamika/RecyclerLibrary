package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list.recylerviewhelper.ItemClickListener
import com.example.list.recylerviewhelper.ItemTouchHelperCallback

class MainActivity : AppCompatActivity() {

    var list: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addData()

        val myrecyclerview : RecyclerView  = findViewById(R.id.my_list)
        var myAdapter = PersonAdapter(list)

        myrecyclerview.hasFixedSize()
        myrecyclerview.layoutManager = LinearLayoutManager(this);
        myrecyclerview.adapter = myAdapter;

        // set touch helper
        val callback = ItemTouchHelperCallback(
            myAdapter,
            isItemSwipeEnabled = true,
            isItemRigtEnabled = true,
            isLongPressEnabled = true
        )



        val helper = ItemTouchHelper(callback)
         helper.attachToRecyclerView(myrecyclerview)

        myrecyclerview.addOnItemTouchListener(
            ItemClickListener(
                this,
                object : ItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val value = "Clicked Item " + list[position] + " at " + position

                        Log.d("TAG", value)
                        Toast.makeText(this@MainActivity, value, Toast.LENGTH_SHORT).show()
                    }
                })
        )


    }

    private fun addData() {
        for (i in 1..10)
            if(i % 2 == 0)
                list.add("First-Name")
            else
                list.add("Second-Name")
    }
}
