package com.example.tkulife_pro.admin.fixreport.FixTab

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.tkulife_pro.admin.fixreport.FixNotification
import com.example.tkulife_pro.databinding.FragmentProcessBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class Process : Fragment(),PageAdapter.OnItemClick {
    private lateinit var binding: FragmentProcessBinding
    private lateinit var viewAdapter: PageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProcessBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        OkHttpUtil.mOkHttpUtil.getAsync("https://tkudorm.site/repairList/con",object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                val res = response.body?.string()
                activity?.runOnUiThread{
                    setRecyclerView(JSONArray(res))
                }
            }

            override fun onFailure(e: IOException) {

            }
        })
    }


    private fun initView(){
        viewAdapter = PageAdapter(this)
    }

    private fun setRecyclerView(adapterData: JSONArray) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.Process.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )
            adapter = viewAdapter
        }
        viewAdapter.data = adapterData
    }



    override fun onItemClick(position: Int) {
        val confirm = AlertDialog.Builder(requireContext())
        confirm.setMessage("確認要將狀態改為可使用嗎?")
        confirm.setTitle("確認視窗")
        confirm.setNegativeButton("取消", null)
        confirm.setPositiveButton("確定",object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Log.d("index",position.toString())
                OkHttpUtil.mOkHttpUtil.put("https://tkudorm.site/repair", JSONObject("{'index':'${position}' , 'con' : 'usable' ,'key' : 'con'} "),object : OkHttpUtil.ICallback{
                    override fun onResponse(response: Response) {
                        val res = response.body?.string()
                        activity?.runOnUiThread{
                            Toast.makeText(requireContext(),
                                JSONObject(res)["msg"].toString(),
                                Toast.LENGTH_LONG).show()
                                onResume()
                        }
                    }
                    override fun onFailure(e: IOException) {

                    }
                })
            }

        }).show()
    }

}