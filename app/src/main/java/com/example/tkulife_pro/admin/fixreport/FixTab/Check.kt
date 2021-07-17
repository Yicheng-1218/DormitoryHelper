package com.example.tkulife_pro.admin.fixreport.FixTab

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
import com.example.tkulife_pro.admin.fixreport.FixNotification
import com.example.tkulife_pro.databinding.FragmentCheckBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class Check: Fragment(),PageAdapter.OnItemClick {
    private lateinit var binding: FragmentCheckBinding
    private lateinit var viewAdapter: PageAdapter
    private lateinit var json: JSONArray
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }
    private fun initView(){
        binding.progressBar.isVisible=true
        viewAdapter = PageAdapter(this)
        OkHttpUtil.mOkHttpUtil.getAsync("https://tkudorm.site/repairList",object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                val res = response.body?.string()
                json= JSONArray(res)
                activity?.runOnUiThread{
                    setRecyclerView(json)
                    binding.progressBar.isVisible=false
                }
            }

            override fun onFailure(e: IOException) {

            }
        })

//        val listener = object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                    val res=snapshot.value as HashMap<*,*>
//                    val nodeList=res[machineType] as HashMap<*,*>
//                    setRecyclerView(nodeList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        }
    }



    private fun setRecyclerView(adapterData:JSONArray) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.Check.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
            )
            adapter = viewAdapter
        }
        viewAdapter.data= adapterData

    }

    override fun onItemClick(position: Int) {
        val item = json[position] as JSONObject
        val machine = item["machine"] as JSONObject
        Intent(requireContext(),DescribePage::class.java).apply{
            putExtra("rep",machine["rep"].toString())
            startActivity((this))
        }
    }
}