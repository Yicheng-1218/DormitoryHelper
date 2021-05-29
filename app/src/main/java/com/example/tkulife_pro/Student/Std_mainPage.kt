package com.example.tkulife_pro.Student

import com.example.tkulife_pro.Student.reminder.PushNotification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.R
import kotlinx.android.synthetic.main.activity_std_mainpage.*

class Std_mainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_std_mainpage)
        button2.setOnClickListener {
            super.onBackPressed()
        }
        imageButton5.setOnClickListener {
            Intent(this,PushNotification::class.java).apply {
                startActivity(this)
            }
        }
    }
}