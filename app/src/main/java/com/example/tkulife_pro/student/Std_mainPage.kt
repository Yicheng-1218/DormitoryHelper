package com.example.tkulife_pro.student

import com.example.tkulife_pro.student.reminder.PushNotification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.student.laundry.Laundry
import com.example.tkulife_pro.databinding.ActivityStdMainpageBinding

class Std_mainPage : AppCompatActivity() {
    private lateinit var binding: ActivityStdMainpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStdMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }
    private fun initView(){
        binding.button2.setOnClickListener {
            super.onBackPressed()
        }
        binding.imageButton5.setOnClickListener {
            Intent(this,PushNotification::class.java).apply {
                startActivity(this)
            }
        }
        binding.imageButton6.setOnClickListener {
            Intent(this, Laundry::class.java).apply {
                startActivity(this)
            }
        }
    }
}