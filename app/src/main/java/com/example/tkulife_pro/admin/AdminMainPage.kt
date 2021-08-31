package com.example.tkulife_pro.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.R
import com.example.tkulife_pro.admin.fixReport.FixNotification
import com.example.tkulife_pro.admin.packageManagement.PackageRecord
import com.example.tkulife_pro.admin.packageManagement.PushNotification
import com.example.tkulife_pro.databinding.ActivityAdminMainPageBinding

class AdminMainPage : AppCompatActivity() {
    private lateinit var binding:ActivityAdminMainPageBinding
    private lateinit var viewModel: FixReportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        viewModel=ViewModelProvider(this).get(FixReportViewModel::class.java)
        viewModel.getRepList().observe(this,{ data->
            val count=data.length()
            Log.d("API",count.toString())
            if (count==0){
                binding.textView53.isVisible=false
            }else{
                binding.textView53.isVisible=true
                binding.textView53.text= data.length().toString()
            }

        })
//        設定BAR
        BarTool(this).setBundle("管理員", R.color.barBlue)

//        包裹管理按鈕
        binding.imageButton7.setOnClickListener{
            Intent(this,PushNotification::class.java).apply {
                startActivity((this))
            }
        }

//        報修通知
        binding.imageButton4.setOnClickListener{
            Intent(this,FixNotification::class.java).apply{
                startActivity(this)
            }
        }
//        包裹簽收紀錄
        binding.imageButton16.setOnClickListener {
            Intent(this,PackageRecord::class.java).apply {
                startActivity(this)
            }
        }
    }
}