package com.example.tkulife_pro.student.laundry.status.floors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tkulife_pro.databinding.ActivityFloorStatusBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FloorStatus : AppCompatActivity() {
    private lateinit var binding: ActivityFloorStatusBinding
    private lateinit var dataType:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloorStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){

//        取得資料種類
        dataType= intent.getStringExtra("DataType").toString()
        val fragments= arrayListOf(BuildingA(dataType),BuildingB(dataType),BuildingC(dataType))
        val pageAdapter = PageAdapter(supportFragmentManager, lifecycle,fragments)
        binding.ViewPager.adapter = pageAdapter

        val title: ArrayList<String> = arrayListOf("A棟", "B棟", "C棟")

        TabLayoutMediator(binding.tabLayout, binding.ViewPager){
            tab, position ->
            tab.text = title[position]
        }.attach()
    }
}