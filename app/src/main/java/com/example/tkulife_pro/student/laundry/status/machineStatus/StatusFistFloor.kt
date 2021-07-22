package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tkulife_pro.databinding.ActivityStatusFistFloorBinding

class StatusFistFloor : AppCompatActivity() {
    private lateinit var binding: ActivityStatusFistFloorBinding
    private val viewAdapter= StatusAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStatusFistFloorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()


    }

    private fun initView() {
        binding.button16.setOnClickListener {
            super.onBackPressed()
        }

        binding.button17.setOnClickListener {
            var dialog = MachineDiagram()
            dialog.show(supportFragmentManager,"machineDiagram")
        }
    }
}