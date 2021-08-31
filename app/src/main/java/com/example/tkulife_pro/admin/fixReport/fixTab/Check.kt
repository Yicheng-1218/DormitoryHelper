package com.example.tkulife_pro.admin.fixReport.fixTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.admin.FixReportViewModel
import com.example.tkulife_pro.databinding.FragmentCheckBinding
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class Check: Fragment(),RepairAdapter.OnItemClick {
    private lateinit var binding: FragmentCheckBinding
    private val viewAdapter= RepairAdapter(this)
    private lateinit var viewModel:FixReportViewModel
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

        viewModel.getRepList().observe(requireActivity(),{ data->
            activity?.runOnUiThread {
                upDateRecycler(data)
                binding.progressBar.isVisible=false
                if (data.length()==0){
                    binding.imageView24.isVisible=true
                    binding.textView51.isVisible=true
                }else{
                    binding.imageView24.isVisible=false
                    binding.textView51.isVisible=false
                }
            }
        })
    }
    private fun initView(){
//        建立viewModel
        viewModel=ViewModelProvider(requireActivity()).get(FixReportViewModel::class.java)
//        開啟loading圖示
        binding.progressBar.isVisible=true
        setRecyclerView()

        binding.imageView24.isVisible=false
        binding.textView51.isVisible=false
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
    override fun onItemClick(position: Int,id:String) {
        val item = json[position] as JSONObject
        val machine = item["machine"] as JSONObject
//    導向至描述頁面
        Intent(requireContext(),DescribePage::class.java).apply{
//            機器狀態
            putExtra("rep",machine["rep"].toString())
//            機器陣列index
            putExtra("index",position.toString())
//            機器ID
            putExtra("id",id)
            startActivity((this))
        }
    }
}