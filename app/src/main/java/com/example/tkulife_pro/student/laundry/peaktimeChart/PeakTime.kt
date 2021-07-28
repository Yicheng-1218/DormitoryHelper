package com.example.tkulife_pro.student.laundry.peaktimeChart

import android.graphics.Color
import android.os.Bundle
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.PeakTimeTabBinding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList

class PeakTime : AppCompatActivity() {
    private lateinit var binding: PeakTimeTabBinding
    private val dataSet = ArrayList<ArrayList<Float>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= PeakTimeTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
//        假設資料集
        dataSet.add(arrayListOf(10f,16f,8f,20f,6f,2f,5f,13f))
        dataSet.add(arrayListOf(5f,13f,6f,5f,6f,12f,10f,6f))
        dataSet.add(arrayListOf(8f,11f,10f,2f,16f,20f,15f,10f))
        dataSet.add(arrayListOf(12f,6f,18f,20f,9f,9f,17f,10f))
        dataSet.add(arrayListOf(3f,17f,20f,15f,14f,5f,9f,7f))
        dataSet.add(arrayListOf(2f,8f,18f,20f,16f,6f,4f,16f))
        dataSet.add(arrayListOf(5f,6f,18f,14f,10f,3f,15f,10f))

//        初始化barChart為資料1
        setBarChart(0)

//        TAB點擊監聽
        binding.tabLayout.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setBarChart(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


    }

//    設定barChart
    private fun setBarChart(position : Int){
        val xLabel = arrayListOf("08","10","12","14","16","18","20","22")
        val entry = ArrayList<BarEntry>().also {
            for((i,e) in dataSet[position].withIndex()){
                it.add(BarEntry(e,i))
            }
        }
        val barDataset = BarDataSet(entry,null).apply {
//            資料字體大小
            valueTextSize=16f
        }
//    長條圖顏色
        barDataset.color=R.color.black
        val data =BarData(xLabel,barDataset)
        binding.barChart.apply {
//            xLabel
            xAxis.apply {
                this.position = XAxis.XAxisPosition.BOTTOM
                textSize=13f
            }
//            yLabel
            axisLeft.textSize=11f
            axisRight.textSize=11f
//            加入資料集
            this.data = data
//            設定背景色
            setBackgroundColor(Color.WHITE)
//            圖表動畫
            animateXY(2000,2000)
//            圖表描述
            setDescription("每兩小時統計一次")
//            描述字體大小
            setDescriptionTextSize(16f)
        }

    }
}

