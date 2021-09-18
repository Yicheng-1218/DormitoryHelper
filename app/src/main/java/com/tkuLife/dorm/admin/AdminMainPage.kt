package com.tkuLife.dorm.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.tkuLife.dorm.BarTool
import com.tkuLife.dorm.R
import com.tkuLife.dorm.admin.fixReport.FixNotification
import com.tkuLife.dorm.admin.packageManagement.PackageRecord
import com.tkuLife.dorm.admin.packageManagement.PushNotification
import com.tkuLife.dorm.databinding.ActivityAdminMainPageBinding

class AdminMainPage : AppCompatActivity() {
    private lateinit var binding:ActivityAdminMainPageBinding
    private lateinit var viewModel: FixReportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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