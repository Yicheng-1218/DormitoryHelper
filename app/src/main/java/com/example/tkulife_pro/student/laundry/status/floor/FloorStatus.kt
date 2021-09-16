package com.example.tkulife_pro.student.laundry.status.floor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityFloorStatusBinding
import com.example.tkulife_pro.student.laundry.status.MachineViewModel
import com.example.tkulife_pro.student.laundry.status.machineStatus.StatusFirstFloor
import com.example.tkulife_pro.student.laundry.status.machineStatus.StatusWithTab

class FloorStatus : AppCompatActivity() , FloorAdapter.OnItemClick{
    private lateinit var machineType :String
    private lateinit var binding:ActivityFloorStatusBinding
    private lateinit var viewModel:MachineViewModel
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
        //        取得intent機器種類
        machineType = intent.getStringExtra("DataType")!!
        val ref= mapOf(
            "Dryer" to "烘衣機",
            "Washer" to "洗衣機"
        )
//        設定BAR
        BarTool(this).setBundle(ref[machineType]!!, R.color.barBlue)

//        開啟loading圖示
        binding.progressBar2.isVisible = true


//        取得viewModel
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
//        viewModel資料監聽
        viewModel.getRealtimeData().observe(this, { data->
//            更新recyclerView
            upDateRecycler(data)
//            關閉loading圖示
            binding.progressBar2.isVisible = false
        })

        setRecycleView()
    }

//    設定recyclerView
    private fun setRecycleView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.RecyclerView2.apply {

            addItemDecoration(
                DividerItemDecoration(this@FloorStatus,
                    DividerItemDecoration.VERTICAL
                )
            )

            setHasFixedSize(true)
            setLayoutManager(layoutManager)

            adapter = viewAdapter //只建立一次FloorAdapter
        }
        viewAdapter.machineType = machineType
    }
    private fun upDateRecycler(adapterData:HashMap<*,*>){
        viewAdapter.machineData = getUsable(adapterData)
    }

    private fun getUsable(data: HashMap<*, *>):Array<Int>{
        val type = data[machineType] as HashMap<*,*>
        val total = arrayOf(0,0,0,0,0,0)
        for (F in type.keys ){
            if(F!="1F"){
                F as String
                when (F[3]){
                    '2' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[1]++
                            }
                        }
                    }
                    '3' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[2]++
                            }
                        }
                    }
                    '4' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[3]++
                            }
                        }
                    }
                    '5' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[4]++
                            }
                        }
                    }
                    '6' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[5]++
                            }
                        }
                    }
                }
            }else{
                for (i in type["1F"] as ArrayList<HashMap<*,*>>) {
                    if(i["con"]=="usable"){
                        total[0]++
                    }
                }
            }
        }
        Log.d("value", "總共$total")
        return total
    }



//    元素點擊監聽
    override fun onItemClick(position: Int) {
        if(position==0){
//            點擊一樓選項
            Intent(this, StatusFirstFloor::class.java).apply {
                putExtra("DataType",machineType)
                startActivity(this)
            }
        }else {
//            點擊一樓以外選項
            Intent(this, StatusWithTab::class.java).apply {
                putExtra("DataType",machineType)
                putExtra("selectPos",(position+1).toString())
                startActivity(this)
            }
        }
    }

}