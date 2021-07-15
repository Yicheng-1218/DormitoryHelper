package com.example.tkulife_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.admin.AdminMainPage
import com.example.tkulife_pro.databinding.ActivityUserSelectBinding
import com.example.tkulife_pro.student.StdMainPage
import java.security.Guard


class UserSelect : AppCompatActivity() {
    private lateinit var binding: ActivityUserSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
//        返回鍵
        binding.button3.setOnClickListener {
            super.onBackPressed()
        }
//        學生主頁
        binding.imageButton3.setOnClickListener {
            Intent(this,StdMainPage::class.java).apply {
                startActivity(this)
            }
        }
//        管理員主頁
        binding.imageButton2.setOnClickListener {
            Intent(this,AdminMainPage::class.java).apply {
                startActivity(this)
            }
        }
//        清除帳密
        binding.button6.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("是否清除帳密")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("是", null)
            confirm.setPositiveButton("否", null)
            confirm.show()
        }
    }

    private fun Guard(){
        val noAccess= AlertDialog.Builder(this)
        noAccess.setMessage("您沒有管理員權限")
        noAccess.setTitle("錯誤")
        noAccess.setPositiveButton("我知道了",null)
        noAccess.show()
    }
}