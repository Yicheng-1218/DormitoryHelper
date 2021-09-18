package com.tkuLife.dorm.admin.fixReport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tkuLife.dorm.BarTool
import com.tkuLife.dorm.R
import com.tkuLife.dorm.admin.fixReport.fixTab.Check
import com.tkuLife.dorm.admin.fixReport.fixTab.Process
import com.tkuLife.dorm.databinding.ActivityFixNotificationBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.ArrayList

class FixNotification : AppCompatActivity() {
    private lateinit var binding:ActivityFixNotificationBinding

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    private fun initView(){
//        設定BAR
        BarTool(this).setBundle("報修通知", R.color.barBlue)



//        設定viewPager畫面內容
        val fragments = arrayListOf(Check(),Process())
        binding.ViewPager.adapter=object :FragmentStateAdapter(supportFragmentManager,lifecycle){
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }

//        設定Tab標題
        val title: ArrayList<String> = arrayListOf("審核中","處理中")
        TabLayoutMediator(binding.tabLayout2,binding.ViewPager){
            tab, position ->
            tab.text=title[position]
        }.attach()

    }

}