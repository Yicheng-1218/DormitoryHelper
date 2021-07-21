package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityStatusFistFloorBinding

class StatusFistFloor : AppCompatActivity() {
    private lateinit var binding: ActivityStatusFistFloorBinding
    private val viewAdapter= StatusAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStatusFistFloorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initView(){
//        返回鍵
        binding.button16.setOnClickListener {
            onBackPressed()
        }

//        示意圖
        binding.button17.setOnClickListener {
            object :DialogFragment(){
            }.show(supportFragmentManager,"dialog")
        }
    }
}