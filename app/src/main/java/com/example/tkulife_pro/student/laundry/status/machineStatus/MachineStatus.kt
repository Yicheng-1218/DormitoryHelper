package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.databinding.ActivityMachineStatusBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

//        返回鍵
        binding.button10.setOnClickListener {
            super.onBackPressed()
        }


        //        建立database實例
        database = Firebase.database.reference

//        取得intent資料
        val dataType=intent.getStringExtra("DataType")
        val floor=intent.getStringExtra("floor")


        //        realtime監聽
        val listener=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                接收廣播
                val res=snapshot.value as HashMap<*,*>
                val nodeList=res[dataType] as HashMap<*,*>
//                建立recyclerView
                setRecyclerView(nodeList[floor] as ArrayList<Map<*, *>>)


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