package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.databinding.ActivityStatusTabBinding

class StatusWithTab : AppCompatActivity() {
    private lateinit var binding: ActivityStatusTabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStatusTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}