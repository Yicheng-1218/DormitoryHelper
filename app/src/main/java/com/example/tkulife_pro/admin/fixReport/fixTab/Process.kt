package com.example.tkulife_pro.admin.fixReport.fixTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.databinding.FragmentProcessBinding
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class Process : Fragment(),RepairAdapter.OnItemClick {
    private lateinit var binding: FragmentProcessBinding
    private val viewAdapter=RepairAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProcessBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        request報修清單(con)
        OkHttpUtil.mOkHttpUtil.getAsync("https://tkudorm.site/repairList/con",object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
//                回傳報修清單陣列
                val res = response.body?.string()

                activity?.runOnUiThread{
//                    UI線程
                    upDateRecycler(JSONArray(res))
//                    關閉loading圖示
                    binding.progressBar3.isVisible=false

                    if (JSONArray(res).length()==0){
                        binding.imageView25.isVisible=true
                        binding.textView52.isVisible=true
                    }else{
                        binding.imageView25.isVisible=false
                        binding.textView52.isVisible=false
                    }
                }
            }

            override fun onFailure(e: IOException) {

            }
        })
    }

    private fun initView(){
//        開啟loading圖示
        binding.progressBar3.isVisible=true
        setRecyclerView()
        binding.imageView25.isVisible=false
        binding.textView52.isVisible=false
    }

//    設定recyclerView
    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.Process.apply {

            addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )

            setHasFixedSize(true)
            setLayoutManager(layoutManager)

            adapter = viewAdapter
        }

    }
    private fun upDateRecycler(adapterData: JSONArray){
        viewAdapter.data = adapterData
    }


//    處理中頁面元素監聽
    override fun onItemClick(position: Int) {
//    顯示對話框
        val confirm = AlertDialog.Builder(requireContext())
        confirm.setMessage("確認要將狀態改為可使用嗎?")
        confirm.setTitle("確認視窗")
        confirm.setNegativeButton("取消", null)
        confirm.setPositiveButton("確定") { _, _ ->
//            管理員put路由(usable)
            OkHttpUtil.mOkHttpUtil.put("https://tkudorm.site/repair",
                JSONObject("{'index':'${position}' , 'con' : 'usable' ,'key' : 'con'} "),
                object : OkHttpUtil.ICallback {
                    override fun onResponse(response: Response) {
                        val res = response.body?.string()
                        activity?.runOnUiThread {
//                            顯示server回傳
                            Toast.makeText(
                                requireContext(),
                                JSONObject(res!!)["msg"].toString(),
                                Toast.LENGTH_LONG
                            ).show()
//                            重新讀取清單
                            onResume()
                        }
                    }

                    override fun onFailure(e: IOException) {

                    }
                })
        }.show()
    }

}