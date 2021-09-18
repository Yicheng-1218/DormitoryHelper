package com.tkuLife.dorm.admin.packageManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tkuLife.dorm.BarTool
import com.tkuLife.dorm.Keyboard
import com.tkuLife.dorm.R
import com.tkuLife.dorm.databinding.ActivityPackageRecordBinding

class PackageRecord : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityPackageRecordBinding
    private lateinit var viewModel:PackageViewModel
    private lateinit var viewAdapter:RecordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPackageRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
//        設定BAR
        BarTool(this).setBundle("包裹簽收紀錄",R.color.barBlue)


//        取得ViewModel
        viewModel = ViewModelProvider(this).get(PackageViewModel::class.java)
        viewModel.context=this
//        搜尋按鈕
        binding.imageButton9.setOnClickListener {
            currentFocus?.clearFocus()
            Keyboard.hide(this,it)
            binding.roomNumber.text.run {
                if (this.isNotEmpty()){
                    viewModel.searchByID(this.toString()).observe(this@PackageRecord,{ data->
                        searchMode(binding.spinner4.selectedItemPosition)
                        upDateRecycler(data)
                    })
                }else{
//                    Toast.makeText(this@PackageRecord,"輸入框不可空白",Toast.LENGTH_SHORT).show()
                    searchMode(0)
                    viewModel.getSortList().value?.let { it1 -> upDateRecycler(it1) }
                }
            }
        }

        viewModel.getSortList().observe(this,{
            Log.d("model record",it.toString())
            upDateRecycler(it)
        })


//        spinner
        binding.spinner4.apply {
//            元素監聽
            binding.spinner4.onItemSelectedListener=this@PackageRecord
//            元素內容
            adapter=ArrayAdapter(this@PackageRecord, R.layout.support_simple_spinner_dropdown_item,
                listOf("最新","已領","未領"))

        }
        setRecyclerView()

    }

    private fun setRecyclerView(){
        viewAdapter= RecordAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.packageRecycler2.apply {

            addItemDecoration(
                DividerItemDecoration(
                    this@PackageRecord,
                    DividerItemDecoration.VERTICAL
                )
            )

            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            adapter=viewAdapter
        }
    }
    private fun upDateRecycler(packageList: ArrayList<HashMap<*,*>>){
        viewAdapter.packageList=packageList
    }
    private fun searchMode(mode:Int){
        viewAdapter.mode=mode
    }

//    元素監聽
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        currentFocus?.clearFocus()
        Keyboard.hide(this,p1!!)
        Log.d("spinner",p2.toString())
        searchMode(p2)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}