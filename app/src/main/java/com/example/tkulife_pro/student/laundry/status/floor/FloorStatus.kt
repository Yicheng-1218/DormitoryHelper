package com.example.tkulife_pro.student.laundry.status.floor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tkulife_pro.databinding.ActivityFloorStatusBinding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel

class FloorStatus : AppCompatActivity() {
    private lateinit var binding:ActivityFloorStatusBinding
    private lateinit var viewModel:SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFloorStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.getRealtimeData().observe(this, { data->
//            TODO UPDATE UI ON HERE
        })
    }
}