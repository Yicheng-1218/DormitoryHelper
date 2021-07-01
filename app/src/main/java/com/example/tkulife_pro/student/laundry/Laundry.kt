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

//        洗衣提醒
        binding.imageButton12.setOnClickListener {
            Intent(this,LittleTimer::class.java).apply {
                startActivity(this)
            }
        }



//        TODO 移植到樓層顯示頁
//        realtime監聽
        val listener=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                接收廣播
                val res=snapshot.value as HashMap<*,*>
                val db=res["Washer"] as HashMap<*,*>
                val map=getUsableCount(db,'A')
//                印出指定樓層可用數量
                Log.d("firebase",map["A-01"].toString())
            }

            override fun onCancelled(error: DatabaseError) {
//                接收廣播失敗
            }

        }
//        新增監聽事件
        database.addValueEventListener(listener)

    }

//    TODO 移植到樓層顯示頁
//    取得每樓層可用機器數量
    private fun getUsableCount(data:HashMap<*,*>,building:Char):MutableMap<String,Int>{
        val res= mutableMapOf<String,Int>()
        for (key in data.keys){
            var count=0
            key as String
            val subKey=key[0]
            if (subKey==building){
                for (field in data[key] as ArrayList<Map<*,*>>){
                    if (field["con"]=="usable"){
                        count++
                    }
                }
                Log.d("firebase","$key:可用台數=$count")
            }
            res[key]=count
        }
        return res
    }
}