package com.example.tkulife_pro.student.stdPackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityPackagePageBinding
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class PackagePage : AppCompatActivity() {
    private lateinit var binding : ActivityPackagePageBinding
    private val viewAdapter = PackageAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackagePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        binding.progressBar4.isVisible = true
        val user = Firebase.auth.currentUser
        val uid = user?.email?.split('@')?: arrayListOf()
        mOkHttpUtil.getAsync("https://tkudorm.site/pklist/${uid[0]}", object :OkHttpUtil.ICallback{
            override fun onResponse(response: Response) {
                val value = response.body?.string()
                runOnUiThread{
                    setRecyclerView(JSONArray(value))
                    binding.progressBar4.isVisible = false
                }

            }

            override fun onFailure(e: IOException) {

            }

        })
    }
    private fun setRecyclerView(packageList : JSONArray){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.packageRecycler.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(
                    this@PackagePage,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = viewAdapter //只建立一次FloorAdapter
        }
        viewAdapter.packageList = packageList
    }
}