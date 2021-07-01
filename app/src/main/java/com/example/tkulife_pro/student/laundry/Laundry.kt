package com.example.tkulife_pro.student.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tkulife_pro.databinding.ActivityLaundryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Laundry : AppCompatActivity() {
    private lateinit var binding: ActivityLaundryBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
//        建立database實例
        database = Firebase.database.reference


        //        返回鍵
        binding.button7.setOnClickListener {
            super.onBackPressed()
        }

//         洗衣提醒
        binding.imageButton12.setOnClickListener {
            Intent(this,LittleTimer::class.java).apply {
                startActivity(this)
            }
        }

//        realtime監聽
        val listener=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val res=snapshot.value as HashMap<*,*>
                val test=res["test"] as HashMap<*,*>

                var count=0
                for (v in test["A-02"] as ArrayList<Map<*,*>>){
                    if (v["con"]=="usable"){
                        count++
                    }
                    Log.d("firebase",v.toString())
                }
                Log.d("firebase","A02可用台數=$count")
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
//        新增監聽事件
        database.addValueEventListener(listener)

    }
    private fun getUsableCount(){
//TODO
    }
}