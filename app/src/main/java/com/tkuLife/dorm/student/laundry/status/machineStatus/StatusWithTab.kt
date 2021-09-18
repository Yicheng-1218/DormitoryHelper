package com.tkuLife.dorm.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tkuLife.dorm.BarTool
import com.tkuLife.dorm.R
import com.tkuLife.dorm.databinding.ActivityStatusTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class StatusWithTab : AppCompatActivity() {
    private lateinit var binding: ActivityStatusTabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStatusTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initView() {
//        取得機器種類及樓層
        val floor = intent.getStringExtra("selectPos")!!
        val type = intent.getStringExtra("DataType")!!
//        設定BAR
        BarTool(this).setBundle("${floor}F洗衣機", R.color.barBlue)
//        設定viewPager畫面內容
        val fragments = arrayListOf(Building1(floor,type,this),Building2(floor,type,this), Building3(floor,type,this))
        binding.ViewPager.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle){
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }

//        設定Tab標題
        val title : ArrayList<String> = arrayListOf("一館", "二館" ,"三館")
        TabLayoutMediator(binding.tabLayout, binding.ViewPager){
            tab, position ->
            tab.text = title[position]
        }.attach()
    }


}