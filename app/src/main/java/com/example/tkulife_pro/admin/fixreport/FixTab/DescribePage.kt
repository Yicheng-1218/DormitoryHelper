package com.example.tkulife_pro.admin.fixreport.FixTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityDescribePageBinding

class DescribePage : AppCompatActivity() {
    private lateinit var binding:ActivityDescribePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDescribePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        val rep = intent.getStringExtra("rep")
        binding.editText.text = rep
    }
}