package com.example.tkulife_pro.admin.fixReport.fixTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.databinding.FragmentCheckBinding
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class Check: Fragment(),RepairAdapter.OnItemClick {
    private lateinit var binding: FragmentCheckBinding
    private lateinit var viewAdapter: RepairAdapter
    private lateinit var json: JSONArray
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
//        request報修清單(rep)
        OkHttpUtil.mOkHttpUtil.getAsync("https://tkudorm.site/repairList/rep",object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
//                回傳報修清單陣列
                val res = response.body?.string()
                json=JSONArray(res)
                activity?.runOnUiThread{
//                    UI線程
                    upDateRecycler(json)

//                    關閉loading圖示
                    binding.progressBar.isVisible=false
                }
            }

            override fun onFailure(e: IOException) {

            }
        })
    }
    private fun initView(){
//        開啟loading圖示
        binding.progressBar.isVisible=true
        viewAdapter = RepairAdapter(this)
        setRecyclerView()
    }


//    設定recyclerView內容
    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.Check.apply {

            addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )

            setHasFixedSize(true)
            setLayoutManager(layoutManager)

            adapter = viewAdapter
        }
    }
    private fun upDateRecycler(adapterData:JSONArray){
        viewAdapter.data= adapterData
    }

//    元素監聽
    override fun onItemClick(position: Int) {
        val item = json[position] as JSONObject
        val machine = item["machine"] as JSONObject
//    導向至描述頁面
        Intent(requireContext(),DescribePage::class.java).apply{
//            機器狀態
            putExtra("rep",machine["rep"].toString())
//            機器陣列index
            putExtra("index",position.toString())
            startActivity((this))
        }
    }
}