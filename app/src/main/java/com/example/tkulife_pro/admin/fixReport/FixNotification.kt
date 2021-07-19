package com.example.tkulife_pro.admin.fixReport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.admin.fixReport.fixTab.Check
import com.example.tkulife_pro.admin.fixReport.fixTab.Process
import com.example.tkulife_pro.admin.fixReport.fixTab.TabAdapter
import com.example.tkulife_pro.databinding.ActivityFixNotificationBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.ArrayList
import org.json.JSONArray

class FixNotification : AppCompatActivity() {
    private lateinit var binding:ActivityFixNotificationBinding
    private lateinit var data:JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFixNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onResume() {
        super.onResume()
//        接收確定報修後導向至處理中頁面
        binding.ViewPager.currentItem=intent.getIntExtra("Page",0)
    }


    private fun initView(){
//        返回鍵
        binding.button12.setOnClickListener{
            super.onBackPressed()
        }


//        建立fragments陣列
        val fragments = arrayListOf(Check(),Process()) as ArrayList<Fragment>
        val pageAdapter = TabAdapter(supportFragmentManager, lifecycle, fragments)
        binding.ViewPager.adapter=pageAdapter

        val title: ArrayList<String> = arrayListOf("審核中","處理中")

        TabLayoutMediator(binding.tabLayout2,binding.ViewPager){
            tab, position ->
            tab.text=title[position]
        }.attach()

    }

}