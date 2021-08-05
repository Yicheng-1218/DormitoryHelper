package com.example.tkulife_pro.admin.packageManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityPackageRecordBinding
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject

class PackageRecord : AppCompatActivity() {
    private lateinit var binding: ActivityPackageRecordBinding
    private val viewAdapter=RecordAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPackageRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



    private fun setRecyclerView(packageList : JSONArray){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recordRecycler.apply{
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(
                    this@PackageRecord,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = viewAdapter
        }
        viewAdapter.packageList = packageList
    }
}