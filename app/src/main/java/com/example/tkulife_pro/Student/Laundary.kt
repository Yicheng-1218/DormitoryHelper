package com.example.tkulife_pro.Student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.databinding.ActivityLaundaryBinding
import com.example.tkulife_pro.databinding.ActivityPushNotificationBinding

class Laundary : AppCompatActivity() {
    private lateinit var binding: ActivityLaundaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLaundaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        binding.button7.setOnClickListener {
            super.onBackPressed()
        }
    }
}