package com.example.tkulife_pro.admin.fixreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.R
import com.example.tkulife_pro.admin.fixreport.FixTab.Check
import com.example.tkulife_pro.admin.fixreport.FixTab.PageAdapter
import com.example.tkulife_pro.admin.fixreport.FixTab.TabAdapter
import com.example.tkulife_pro.databinding.ActivityFixNotificationBinding
import com.example.tkulife_pro.databinding.ActivityMachineStatusBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_fix_notification.*

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

        val fragments = arrayListOf(Check(dataType),com.example.tkulife_pro.admin.fixreport.FixTab.Process(dataType)) as ArrayList<Fragment>
        val pageAdapter = TabAdapter(supportFragmentManager, lifecycle, fragments)
        binding.ViewPager.adapter=pageAdapter

        val title: ArrayList<String> = arrayListOf("審核中","處理中")

        TabLayoutMediator(binding.tabLayout2,binding.ViewPager){
            tab, position ->
            tab.text=title[position]
        }.attach()
    }

}