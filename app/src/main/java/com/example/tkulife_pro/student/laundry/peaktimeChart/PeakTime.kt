package com.example.tkulife_pro.student.laundry.peaktimeChart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.PeakTimeTabBinding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.internal.notify
import java.util.*
import kotlin.collections.ArrayList

class PeakTime : AppCompatActivity() {
    private lateinit var binding: PeakTimeTabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= PeakTimeTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){

        val res=BarChartModel.history[0]
        setBarChart(BarChartModel.countRate(res))



//        TAB點擊監聽
        binding.tabLayout.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                取得某日資料集
                val res=BarChartModel.history[tab!!.position]
                Log.d("bar",BarChartModel.countRate(res).toString())
//                計算使用率: Xi/sum -> ArrayList<Float>
                setBarChart(BarChartModel.countRate(res))
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }



//    設定barChart
    private fun setBarChart(dataSet : ArrayList<Float>){
        val xLabel = arrayListOf("08-10","10-12","12-14","14-16","16-18","18-20","20-22","22-24","24-08")
        val entry = ArrayList<BarEntry>().also {
            for((i,e) in dataSet.withIndex()){
                it.add(BarEntry(e,i))
            }
        }
        val barDataset = BarDataSet(entry,"各時段使用次數/全天使用次數").apply {
            valueFormatter = PercentFormatter()
//            資料字體大小
            valueTextSize=13f
        }
//    長條圖顏色
        barDataset.color=R.color.iconBlue
        val data =BarData(xLabel,barDataset)
        binding.barChart.apply {
//            xLabel
            xAxis.apply {
                this.position = XAxis.XAxisPosition.BOTTOM
                textSize=11f
            }
            xAxis.labelRotationAngle=-60f
            xAxis.spaceBetweenLabels=2
            xAxis.setDrawGridLines(false)
//            yLabel
            axisLeft.axisLineWidth=0.9f
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)
            barDataset.setDrawValues(true)
            //axisLeft.textSize=11f
            //axisRight.textSize=11f
//            加入資料集
            data.notifyDataChanged()
            this.data = data

//            設定背景色
            setBackgroundColor(Color.WHITE)
//            圖表動畫
            animateXY(2000,2000)
//            圖表描述
            setDescription("")
//            描述字體大小
            setDescriptionTextSize(16f)
        }

    }
}

