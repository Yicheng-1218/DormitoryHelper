package com.example.tkulife_pro.student.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tkulife_pro.databinding.ActivityLaundryBinding
import com.example.tkulife_pro.student.laundry.status.floors.FloorStatus
import com.example.tkulife_pro.student.laundry.status.machinestatus.MachineStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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

    }


}