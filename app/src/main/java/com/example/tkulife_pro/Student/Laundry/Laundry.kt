package com.example.tkulife_pro.Student.Laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.databinding.ActivityLaundryBinding


class Laundry : AppCompatActivity() {
    private lateinit var binding: ActivityLaundryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        binding.button7.setOnClickListener {
            super.onBackPressed()
        }
        binding.imageButton12.setOnClickListener {
            Intent(this,LittleTimer::class.java).apply {
                startActivity(this)
            }
        }
    }
}