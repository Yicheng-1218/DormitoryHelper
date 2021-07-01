package com.example.tkulife_pro.Student.Laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.R
import kotlinx.android.synthetic.main.activity_washer_dryer.*
import org.json.JSONArray

private lateinit var adapter:L_Adapter

class washer_dryer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_washer_dryer)
        createwder()
        val linearLayoutManager= LinearLayoutManager(this)
        linearLayoutManager.orientation= LinearLayoutManager.VERTICAL
        recyclerView.layoutManager=linearLayoutManager
        adapter= L_Adapter()
        adapter.jsonArray= JSONArray("[{id:1,condition:'using'},{id:2,condition:'broken'},{id:3,condition:'using'}]")
        recyclerView.adapter=adapter
    }

    class Washer(number:Int,status:String){
        var _number="";
        var _status=""
    }

    class Dryer(number:Int,status:String){
        var _number="";
        var _status=""
    }

    val washers:ArrayList<Washer> = ArrayList<Washer>()
    val d : ArrayList<Dryer> = ArrayList<Dryer>()

    fun createwder(){
        var y: Int=1
        for(i in 1..5){
            washers.add(Washer(y,"可使用"))
            d.add(Dryer(y,"使用中"))
            y+= 1
        }
        var z: Int=6
        for(i in 6..10){
            washers.add(Washer(z,"使用中"))
            d.add(Dryer(z,"可使用"))
            y+= 1
        }
    }
}