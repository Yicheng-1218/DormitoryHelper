package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityStatusFistFloorBinding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel

class StatusFirstFloor : AppCompatActivity() {
    lateinit var machineType : String
    private lateinit var viewModel : SharedViewModel
    private lateinit var binding: ActivityStatusFistFloorBinding
    private val viewAdapter= StatusAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStatusFistFloorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initView(){
        machineType = intent.getStringExtra("DataType")!!
//        返回鍵
        binding.button16.setOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.getRealtimeData().observe(this, { data->
            val type=data[machineType] as HashMap<*,*>
            val machineList = type["1F"] as ArrayList<HashMap<*,*>>
            setRecycleView(machineList)
        })

//        示意圖
        binding.button17.setOnClickListener {
            object :DialogFragment(){
            }.show(supportFragmentManager,"dialog")
        }
    }
    private fun setRecycleView(adapterData:ArrayList<HashMap<*,*>>){
        binding.recyclerView.apply {
            val layoutManager = LinearLayoutManager(this@StatusFirstFloor)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(this@StatusFirstFloor,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = viewAdapter //只建立一次FloorAdapter
        }
        viewAdapter.floor = "1F"
        viewAdapter.machineData = adapterData
    }
}