package com.example.tkulife_pro.admin.fixreport.FixTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityDescribePageBinding

class DescribePage : AppCompatActivity() {
    private lateinit var binding: ActivityDescribePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescribePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()


    }

    private fun initView() {
        val rep = intent.getStringExtra("rep")
        binding.editText.text = rep

        binding.button15.setOnClickListener() {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("確認要將狀態改為故障中嗎?")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("確定", null)
            confirm.setPositiveButton("取消", null)
            confirm.show()
        }
    }
}