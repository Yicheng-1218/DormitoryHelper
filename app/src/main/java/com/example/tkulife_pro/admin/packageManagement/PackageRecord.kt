package com.example.tkulife_pro.admin.packageManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.Keyboard
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityPackagePageBinding
import com.example.tkulife_pro.databinding.ActivityPackageRecordBinding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray

class PackageRecord : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityPackageRecordBinding
    private lateinit var viewModel:PackageViewModel
    private val viewAdapter=RecordAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPackageRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){

//        取得ViewModel
        viewModel = ViewModelProvider(this).get(PackageViewModel::class.java)


//        搜尋按鈕
        binding.imageButton9.setOnClickListener {
            currentFocus?.clearFocus()
            Keyboard.hide(this,it)
        }

//        spinner
        binding.spinner4.apply {
//            元素監聽
            binding.spinner4.onItemSelectedListener=this@PackageRecord
//            元素內容
            adapter=ArrayAdapter(this@PackageRecord, R.layout.support_simple_spinner_dropdown_item,
                listOf("最新","已領","未領"))

        }


    }



    private fun setRecyclerView(packageList: ArrayList<HashMap<*,*>>){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.packageRecycler2.apply {
            if (getLayoutManager()==null){
                addItemDecoration(
                    DividerItemDecoration(
                        this@PackageRecord,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            adapter=viewAdapter
        }
        viewAdapter.packageList=packageList
    }

//    元素監聽
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        currentFocus?.clearFocus()
        Keyboard.hide(this,p1!!)
        Log.d("spinner",p2.toString())
        viewModel.getSortList(p2).observe(this,{
            Log.d("model record",it.toString())
            setRecyclerView(it)
        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}