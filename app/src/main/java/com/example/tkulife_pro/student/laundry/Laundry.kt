package com.example.tkulife_pro.student.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityLaundryBinding
import com.example.tkulife_pro.student.laundry.fixReport.FixPage
import com.example.tkulife_pro.student.laundry.peaktimeChart.PeakTime
import com.example.tkulife_pro.student.laundry.status.floor.FloorStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception


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
//        設定BAR
        BarTool(this).setBundle("洗衣烘衣", R.color.barBlue)

//        取得學號
        val uid= FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
        uid.isNullOrEmpty().let{ if (!it)binding.textView21.text=uid }


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
//        binding.imageButton10.setOnClickListener {
//            Intent(this, FloorStatus::class.java).apply {
//                putExtra("DataType","Dryer")
//                startActivity(this)
//            }
//        }

//        報修按鈕
        binding.imageButton13.setOnClickListener {
            Intent(this,FixPage::class.java).apply {
                startActivity(this)
            }
        }

//        使用率按鈕
        binding.imageButton14.setOnClickListener{
            Intent(this,PeakTime::class.java).apply {
                startActivity(this)
            }
        }
    }
}