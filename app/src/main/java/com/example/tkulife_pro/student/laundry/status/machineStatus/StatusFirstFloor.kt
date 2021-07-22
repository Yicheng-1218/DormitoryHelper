package com.example.tkulife_pro.student.laundry.status.machineStatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        取得intent機器種類
        machineType = intent.getStringExtra("DataType")!!


//        返回鍵
        binding.button16.setOnClickListener {
            onBackPressed()
        }

//        取得viewModel
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
//        viewModel資料監聽
        viewModel.getRealtimeData().observe(this, { data->
            val type=data[machineType] as HashMap<*,*>
            val machineList = type["1F"] as ArrayList<HashMap<*,*>>
            setRecyclerView(machineList)
        })

//        示意圖
        binding.button17.setOnClickListener {
//            建立示意圖物件
            val dialog=MachineDialog()

//            顯示示意圖
            dialog.show(supportFragmentManager,"dialog")
        }
    }

//    設定recyclerView
    private fun setRecyclerView(adapterData:ArrayList<HashMap<*,*>>){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.apply {
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
        viewAdapter.machineType = machineType
    }

//    示意圖專用class
    class MachineDialog:DialogFragment(){
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var rootView:View=inflater.inflate(R.layout.fragment_machine_diagram,container,false)
            rootView.findViewById<View>(R.id.close).setOnClickListener{
                dismiss()
            }
            return rootView
        }
    }
}