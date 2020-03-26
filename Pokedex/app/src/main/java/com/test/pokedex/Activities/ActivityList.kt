package com.test.pokedex.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.Adapters.AdapterList
import com.test.pokedex.R

import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_list.*

class ActivityList : AppCompatActivity() {

    private var context: Context = this

    private lateinit var data:JsonArray
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:AdapterList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        initializeCoponents()
        initializeListeners()
        initializeData()


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun initializeCoponents(){

    }

    fun initializeListeners(){
    }

    fun initializeData(){
        Ion.with(context)
            .load("https://pokeapi.co/api/v2/pokemon/?offset=0&limit=964")
            .asJsonObject()
            .done { e, result ->
                if(e == null){
                    Log.i("Output", result.getAsJsonArray("results").size().toString())

                    data = result.getAsJsonArray("results")

                    initializeList()
                }
            }
    }

    fun initializeList(){
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)


        adapter = AdapterList()
        adapter.AdapterList(context,data)

        recycler_view_list.layoutManager = linearLayoutManager
        recycler_view_list.adapter = adapter
        recycler_view_list.setHasFixedSize(true)
        recycler_view_list.itemAnimator = DefaultItemAnimator()

    }


}


