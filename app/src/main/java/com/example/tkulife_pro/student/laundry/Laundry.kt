package com.example.tkulife_pro.student.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
import com.example.tkulife_pro.databinding.ActivityLaundryBinding
import com.example.tkulife_pro.student.laundry.fixreport.FixPage
import com.example.tkulife_pro.student.laundry.status.floors.FloorStatus
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class Laundry : AppCompatActivity() {
    private lateinit var binding: ActivityLaundryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){


//        返回鍵
        binding.button7.setOnClickListener {
            super.onBackPressed()
        }

//        報修頁面
        binding.imageButton13.setOnClickListener {
            Intent(this,FixPage::class.java).apply {
                startActivity(this)
            }
        }

//        洗衣提醒
        binding.imageButton12.setOnClickListener {
            Intent(this,LittleTimer::class.java).apply {
                startActivity(this)
            }
        }

//        洗衣機按鈕
        binding.imageButton11.setOnClickListener {
            Intent(this,FloorStatus::class.java).apply {
                putExtra("DataType","Washer")
                startActivity(this)
            }
        }

//        烘衣機按鈕
        binding.imageButton10.setOnClickListener {
            Intent(this,FloorStatus::class.java).apply {
                putExtra("DataType","Dryer")
                startActivity(this)
            }
        }

//        報修按鈕
        binding.imageButton13.setOnClickListener {
//            val json=JSONObject("{'email':'123','name':'456','phone':'789','room_bed':'987','package':[{'id':'147','from':'good'}],'id':'789'}")
//            mOkHttpUtil.getAsync("https://test.bestea.space/studentList",object :OkHttpUtil.ICallback{
//                override fun onResponse(response: Response) {
//                    val res=response.body?.string()
//                    try {
//                        Log.d("okhttp",response.toString())
//                        Log.d("okhttp",JSONObject(res).toString())
//                    }catch (e:Exception){
//                        Log.d("okhttp",e.toString())
//                    }
//
//                }
//
//                override fun onFailure(e: IOException) {
//                    Log.d("okhttp",e.toString())
//                }
//
//            })
            Intent(this,FixPage::class.java).apply {
                startActivity(this)
            }
        }
    }


}