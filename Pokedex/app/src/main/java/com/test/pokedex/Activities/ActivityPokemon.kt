package com.test.pokedex.Activities

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.R
import java.util.*

class ActivityPokemon : AppCompatActivity() {
    private var context: Context = this
    private var url: String = ""
    private var numero:String = ""
    private lateinit var data: JsonObject
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var imagePokemon: ImageView
    private lateinit var namePokemon: TextView
    private lateinit var numberPokemon:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        initializeComponents()
        initializeData()
    }

    private fun initializeComponents() {
        imagePokemon = findViewById(R.id.pokemon_image)
        namePokemon = findViewById(R.id.pokemon_name)
        numberPokemon = findViewById(R.id.pokemon_number)
        url = intent.getStringExtra("valor")
        numero=intent.getStringExtra("numero")
    }


    private fun initializeData() {

        Ion.with(context)
            .load(url)
            .asJsonObject()
            .done { e, result ->
                if (e == null) {

                    data = result
                    if (!data.get("sprites").isJsonNull) {

                        if (data.get("sprites").asJsonObject.get("front_default") != null) {
                            //Pintar
                            Glide
                                .with(context)
                                .load(data.get("sprites").asJsonObject.get("front_default").asString)
                                .placeholder(R.drawable.pokemon_logo_min)
                                .error(R.drawable.pokemon_logo_min)
                                .into(imagePokemon)

                        }
                        else {
                            imagePokemon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pokemon_logo_min))
                            }
                    } else {
                        imagePokemon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pokemon_logo_min))
                    }

                    if (data.get("name") != null) {
                        namePokemon.setText(data.get("name").asString.toUpperCase())
                    } else {
                        namePokemon.text = "Name not Found"
                    }

                    numberPokemon.setText("Number: "+numero)

                    if (!data.get("types").isJsonNull) {

                        val types = data.get("types").asJsonArray

                        var i=0
                        while(i < types.size()){

                            val item = types.get(i).asJsonObject.get("type").asJsonObject

                            val typeName = item.get("name").toString().replace("\"", "").toUpperCase(Locale.ROOT)

                            agregarInfo(typeName, R.id.TypesLayout)
                            i++
                        }
                    }

                    if (!data.get("stats").isJsonNull) {
                        val stats = data.get("stats").asJsonArray

                        var i=0
                        while(i < stats.size()) {
                            val stat_base = stats.get(i).asJsonObject.get("base_stat")
                            val stat_name =
                                stats.get(i).asJsonObject.get("stat").asJsonObject.get("name")

                            val result = stat_name.toString().replace("\"", "").toUpperCase(Locale.ROOT) + ": " + stat_base.toString()
                            agregarInfo(result, R.id.StatsLayout)
                            i++
                        }
                    }

                    if (!data.get("moves").isJsonNull) {
                        val moves = data.get("moves").asJsonArray

                        var i=0
                        while(i < moves.size()){
                            val move_name =
                                moves.get(i).asJsonObject.get("move").asJsonObject.get("name")

                            val result = move_name.toString().replace("\"", "").toUpperCase(Locale.ROOT)

                            agregarInfo(result, R.id.MovesLayout)
                            i++
                        }
                    }
                }
                initializeList()
            }


    }

    private fun initializeList() {
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)

    }

    private fun agregarInfo(information: String, layID: Int) {
        val linearLayout: LinearLayout = findViewById(layID)
        var type = TextView(this)
        type.gravity = Gravity.LEFT
        type.textSize = 18f
        type.text = information
        type.setTextColor((Color.parseColor("#F1ECCF")))

        linearLayout.addView(type)
    }
}
