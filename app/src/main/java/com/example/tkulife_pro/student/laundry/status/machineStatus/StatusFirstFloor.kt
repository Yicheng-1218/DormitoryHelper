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
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityStatusFistFloorBinding
import com.example.tkulife_pro.databinding.FragmentMachineDiagramBinding
import com.example.tkulife_pro.student.laundry.status.MachineViewModel

class StatusFirstFloor : AppCompatActivity() {
    lateinit var machineType : String
    private lateinit var viewModel : MachineViewModel
    private lateinit var binding: ActivityStatusFistFloorBinding
    private val viewAdapter= StatusAdapter(this)
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
//        設定BAR
        BarTool(this).setBundle("1F洗衣機",R.color.barBlue)

//        取得intent機器種類
        machineType = intent.getStringExtra("DataType")!!

        setRecyclerView()

//        取得viewModel
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
//        viewModel資料監聽
        viewModel.getRealtimeData().observe(this, { data->
            val type=data[machineType] as HashMap<*,*>
            val machineList = type["1F"] as ArrayList<HashMap<*,*>>
            upDateRecycler(machineList)
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
    private fun setRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.apply {
            if (getLayoutManager()==null){
                addItemDecoration(
                    DividerItemDecoration(this@StatusFirstFloor,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            setHasFixedSize(true)
            setLayoutManager(layoutManager)

            adapter = viewAdapter //只建立一次FloorAdapter
        }

    }

    private fun upDateRecycler(adapterData:ArrayList<HashMap<*,*>>){
        viewAdapter.floor = "1F"
        viewAdapter.machineData = adapterData
        viewAdapter.machineType = machineType
    }

//    示意圖專用class
    class MachineDialog:DialogFragment(){
        lateinit var binding: FragmentMachineDiagramBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding= FragmentMachineDiagramBinding.inflate(layoutInflater)
            binding.close.setOnClickListener {
                dismiss()
            }
            return binding.root
        }
    }

}