package com.example.tkulife_pro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_std_mainpage.*

class Std_mainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_std_mainpage)
        button2.setOnClickListener {
            super.onBackPressed()
        }
    }
}