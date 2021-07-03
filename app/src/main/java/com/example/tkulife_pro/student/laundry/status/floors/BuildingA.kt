package com.example.tkulife_pro.student.laundry.status.floors

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.FragmentBuildingABinding

class BuildingA : Fragment() {
    private lateinit var binding: FragmentBuildingABinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuildingABinding.inflate(layoutInflater)
        initView()
        return binding.root

    }

    private fun initView(){
     //   setRecycleView(getUsableCount())
    }

    private fun setRecycleView(adapterData:MutableMap<String,Int>){
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.BuildA.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = FloorAdapter()
        }

    }
    //    取得每樓層可用機器數量
    private fun getUsableCount(data:HashMap<*,*>,building:Char):MutableMap<String,Int>{
        val res= mutableMapOf<String,Int>()
        for (key in data.keys){
            var count=0
            key as String
            val subKey=key[0]
            if (subKey==building){
                for (field in data[key] as ArrayList<Map<*,*>>){
                    if (field["con"]=="usable"){
                        count++
                    }
                }
                Log.d("firebase","$key:可用台數=$count")
            }
            res[key]=count
        }
        return res
    }


}