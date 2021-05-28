package com.example.tkulife_pro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_user_select.*


class User_select : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_user_select)

    }
}