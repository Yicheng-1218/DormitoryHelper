package com.example.tkulife_pro.student

import com.example.tkulife_pro.student.reminder.PushNotification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.student.laundry.Laundry
import com.example.tkulife_pro.databinding.ActivityStdMainpageBinding
import com.example.tkulife_pro.student.stdPackage.PackagePage

class StdMainPage : AppCompatActivity() {
    private lateinit var binding: ActivityStdMainpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStdMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }
    private fun initView(){
//        返回鍵
        binding.button2.setOnClickListener {
            super.onBackPressed()
        }
//        鎖屏提醒
        binding.imageButton5.setOnClickListener {
            Intent(this,PushNotification::class.java).apply {
                startActivity(this)
            }
        }
//        洗衣烘衣
        binding.imageButton6.setOnClickListener {
            Intent(this, Laundry::class.java).apply {
                startActivity(this)
            }
        }
//        包裹狀態
        binding.imageButton8.setOnClickListener{
            Intent(this, PackagePage::class.java).apply {
                startActivity(this)
            }
        }
    }
}