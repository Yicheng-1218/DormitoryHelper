package com.example.tkulife_pro.student.laundry.status.floors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.databinding.ActivityFloorStatusBinding
import com.google.android.material.tabs.TabLayoutMediator

class FloorStatus : AppCompatActivity() {
    private lateinit var binding: ActivityFloorStatusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloorStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        val pageAdapter = PageAdapter(supportFragmentManager, lifecycle)
            binding.ViewPager.adapter = pageAdapter

        val title: ArrayList<String> = arrayListOf("A棟", "B棟", "C棟")
        TabLayoutMediator(binding.tabLayout, binding.ViewPager){
            tab, position -> tab.text = title[position]
        }.attach()

    }


}