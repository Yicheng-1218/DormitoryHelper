package com.example.tkulife_pro.student.laundry.status.floor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.databinding.ActivityFloorStatusBinding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel

class FloorStatus : AppCompatActivity() , FloorAdapter.OnItemClick{
    private lateinit var binding:ActivityFloorStatusBinding
    private lateinit var viewModel:SharedViewModel
    private var  viewAdapter = FloorAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFloorStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    private fun initView(){
        binding.progressBar2.isVisible = true
        val type = intent.getStringExtra("DataType")
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.getRealtimeData().observe(this, { data->
//            TODO UPDATE UI ON HERE
            setRecycleView(data,type!!)
            //關閉loading圖示
            binding.progressBar2.isVisible = false
        })

        //返回鍵
        binding.button10.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecycleView(adapterData:HashMap<*,*>,machineType:String){
        binding.RecyclerView2.apply {
            val layoutManager = LinearLayoutManager(this@FloorStatus)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(this@FloorStatus,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = viewAdapter //只建立一次FloorAdapter
        }

        viewAdapter.machineData = adapterData
        viewAdapter.machineType = machineType
    }

    override fun onItemClick(position: Int) {
        Log.d("value",position.toString())
    }
}