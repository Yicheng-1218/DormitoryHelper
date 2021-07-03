package com.example.tkulife_pro.student.laundry.status.machinestatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.databinding.ActivityMachineStatusBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array

class MachineStatus : AppCompatActivity() {
    private lateinit var binding:ActivityMachineStatusBinding
    private lateinit var database: DatabaseReference
    private val viewAdapter=StatusAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMachineStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){


        //        建立database實例
        database = Firebase.database.reference


        //        realtime監聽
        val listener=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                接收廣播
                val res=snapshot.value as HashMap<*,*>
                val nodeList=res["Washer"] as HashMap<*,*>
//                建立recyclerView
                setRecyclerView(nodeList["A-01"] as ArrayList<Map<*, *>>)


            }

            override fun onCancelled(error: DatabaseError) {
//                接收廣播失敗
            }

        }
//        新增監聽事件
        database.addValueEventListener(listener)



    }

    private fun setRecyclerView(adapterData:ArrayList<Map<*,*>>){
        val layoutManager= LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        binding.RecyclerView2.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(
                    this@MachineStatus,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter=viewAdapter
        }
        viewAdapter.dataList=adapterData
    }




}