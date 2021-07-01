package com.example.tkulife_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.student.Std_mainPage
import com.example.tkulife_pro.databinding.ActivityUserSelectBinding


class UserSelect : AppCompatActivity() {
    private lateinit var binding: ActivityUserSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.button3.setOnClickListener {
            super.onBackPressed()
        }
        binding.imageButton3.setOnClickListener {
            Intent(this,Std_mainPage::class.java).apply {
                startActivity(this)
            }
        }
        //TODO 管理員視窗
        binding.imageButton2.setOnClickListener {
            val noAccess= AlertDialog.Builder(this)
            noAccess.setMessage("您沒有管理員權限")
            noAccess.setTitle("錯誤")
            noAccess.setPositiveButton("我知道了",null)
            noAccess.show()
        }
        //TODO 確認視窗
        binding.button6.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("是否清除帳密")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("是",null)
            confirm.setPositiveButton("否",null)
            confirm.show()
        }
    }

}