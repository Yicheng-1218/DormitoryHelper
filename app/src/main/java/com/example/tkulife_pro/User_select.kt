package com.example.tkulife_pro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
    }
}