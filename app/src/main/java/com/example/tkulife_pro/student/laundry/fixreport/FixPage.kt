package com.example.tkulife_pro.student.laundry.fixreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.tkulife_pro.databinding.ActivityFixPageBinding
import com.example.tkulife_pro.databinding.ActivityLaundryBinding
import kotlinx.android.synthetic.main.activity_fix_page.*

class FixPage : AppCompatActivity() {
    private lateinit var binding: ActivityFixPageBinding
    val building= arrayListOf<String>("一館","二館","三館","洗衣場")
    var idc_building=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFixPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        set_spinner2()
        val arrayAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,idc_building)
        spinner2.adapter=arrayAdapter
    }

    fun set_spinner2(){
        if(spinner1.selectedItemPosition==0){
            idc_building.add(building[3])
        }else{
            for(i in 0..2){
                idc_building.add(building[i])
            }
        }
    }



}