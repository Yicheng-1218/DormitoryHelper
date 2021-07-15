package com.example.tkulife_pro.student.laundry.fixreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.tkulife_pro.databinding.ActivityFixPageBinding

class FixPage : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityFixPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFixPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        binding.spinner1.onItemSelectedListener=this

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2==0){
            binding.spinner2.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayOf("洗衣場"))
        }else{
            binding.spinner2.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayOf("一館","二館","三館"))
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}