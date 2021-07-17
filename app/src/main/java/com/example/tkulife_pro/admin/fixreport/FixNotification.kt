package com.example.tkulife_pro.admin.fixreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.admin.fixreport.FixTab.TabAdapter
import com.example.tkulife_pro.databinding.ActivityFixNotificationBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.ArrayList


class FixNotification : AppCompatActivity() {
    private lateinit var binding:ActivityFixNotificationBinding
    private lateinit var dataType:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFixNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){

        binding.button12.setOnClickListener{
            super.onBackPressed()
        }

        dataType=intent.getStringExtra("DataType").toString()

        val fragments = arrayListOf(com.example.tkulife_pro.admin.fixreport.FixTab.Check(dataType),com.example.tkulife_pro.admin.fixreport.FixTab.Process(dataType)) as ArrayList<Fragment>
        val pageAdapter = TabAdapter(supportFragmentManager, lifecycle, fragments)
        binding.ViewPager.adapter=pageAdapter

        val title: ArrayList<String> = arrayListOf("審核中","處理中")

        TabLayoutMediator(binding.tabLayout2,binding.ViewPager){
            tab, position ->
            tab.text=title[position]
        }.attach()
    }

}