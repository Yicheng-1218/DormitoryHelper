package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.databinding.ActivityStatusTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class StatusWithTab : AppCompatActivity() {
    private lateinit var binding: ActivityStatusTabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStatusTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.button11.setOnClickListener(){
            super.onBackPressed()
        }

        val fragments = arrayListOf(Building1(),Building2(), Building3()) as ArrayList<Fragment>
        val pageAdapter = PageAdapter(supportFragmentManager, lifecycle, fragments)
        binding.ViewPager.adapter = pageAdapter

        val title : ArrayList<String> = arrayListOf("一館", "二館" ,"三館")

        TabLayoutMediator(binding.tabLayout, binding.ViewPager){
            tab, position ->
            tab.text = title[position]
        }.attach()
    }


}