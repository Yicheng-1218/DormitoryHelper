package com.example.tkulife_pro.student.laundry.peaktimeChart

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityPeakTimeBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class PeakTime : AppCompatActivity() {
    private lateinit var binding: ActivityPeakTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPeakTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){

        // x axis values
        val xValues=ArrayList<String>()
        xValues.add("08:00")
        xValues.add("09:00")
        xValues.add("10:00")
        xValues.add("11:00")
        xValues.add("12:00")
        xValues.add("13:00")

        //bar Entries
        val barEntries=ArrayList<BarEntry>()
        barEntries.add(BarEntry(3f,0))
        barEntries.add(BarEntry(7f,1))
        barEntries.add(BarEntry(14f,2))
        barEntries.add(BarEntry(21f,3))
        barEntries.add(BarEntry(8f,4))
        barEntries.add(BarEntry(9f,5))

        // bar Dataset
        val barDataset=BarDataSet(barEntries,null)
        barDataset.color=R.color.iconBlue

        //  make a bar data
        val data =BarData(xValues,barDataset)
        binding.barChart.data=data

        binding.barChart.setBackgroundColor(Color.WHITE)
        binding.barChart.animateXY(3000,3000)

    }
}

