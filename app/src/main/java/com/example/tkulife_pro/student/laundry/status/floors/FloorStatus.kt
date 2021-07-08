package com.example.tkulife_pro.student.laundry.status.floors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.databinding.ActivityFloorStatusBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.ArrayList

class FloorStatus : AppCompatActivity(){
    private lateinit var binding: ActivityFloorStatusBinding
    private lateinit var dataType:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloorStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){

        binding.button11.setOnClickListener{
            super.onBackPressed()
        }
//        取得資料種類
        dataType= intent.getStringExtra("DataType").toString()
        val fragments= arrayListOf(BuildingA(dataType),BuildingB(dataType),BuildingC(dataType)) as ArrayList<Fragment>
        val pageAdapter = PageAdapter(supportFragmentManager, lifecycle,fragments)
        binding.ViewPager.adapter = pageAdapter

        val title: ArrayList<String> = arrayListOf("一館", "二館", "三館")

        TabLayoutMediator(binding.tabLayout, binding.ViewPager){
            tab, position ->
            tab.text = title[position]
        }.attach()
    }


}