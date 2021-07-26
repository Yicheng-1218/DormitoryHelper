package com.example.tkulife_pro.student.laundry.peaktimeChart

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

    fun initView(){

        // x axis values
        val xvalues=ArrayList<String>()
        xvalues.add("08:00")
        xvalues.add("09:00")
        xvalues.add("10:00")
        xvalues.add("11:00")
        xvalues.add("12:00")
        xvalues.add("13:00")

        //bar entries
        val barentries=ArrayList<BarEntry>()
        barentries.add(BarEntry(3f,0))
        barentries.add(BarEntry(7f,0))
        barentries.add(BarEntry(14f,0))
        barentries.add(BarEntry(21f,0))
        barentries.add(BarEntry(8f,0))
        barentries.add(BarEntry(9f,0))

        // bardata set
        val bardataset=BarDataSet(barentries,null)
        bardataset.color=R.color.iconBlue

        //  make a bar data
        val data =BarData(xvalues,bardataset)
        binding.barChart.data=data

        binding.barChart.setBackgroundColor(resources.getColor(R.color.white))
        binding.barChart.animateXY(3000,3000)

    }
}

