package com.example.tkulife_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.Student.Std_mainPage
import kotlinx.android.synthetic.main.activity_std_mainpage.*
import kotlinx.android.synthetic.main.activity_user_select.*


class User_select : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)
        imageButton2.setOnClickListener {
            val no_access= AlertDialog.Builder(this)
            no_access.setMessage("您沒有管理員權限")
            no_access.setTitle("錯誤")
            no_access.setPositiveButton("我知道了",null)
            no_access.show()
        }
        button3.setOnClickListener {
            super.onBackPressed()
        }
        imageButton3.setOnClickListener {
            Intent(this,Std_mainPage::class.java).apply {
                startActivity(this)
            }
        }
    }


}