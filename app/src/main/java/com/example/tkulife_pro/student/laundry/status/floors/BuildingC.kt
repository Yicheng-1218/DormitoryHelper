package com.example.tkulife_pro.student.laundry.status.floors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.databinding.FragmentBuildingCBinding
import com.example.tkulife_pro.student.laundry.status.machineStatus.MachineStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class BuildingC(private val machineType:String) : Fragment(),FloorAdapter.OnItemClick {
    private lateinit var binding: FragmentBuildingCBinding
    private lateinit var database: DatabaseReference
    private lateinit var viewAdapter: FloorAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBuildingCBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }


    private fun initView(){
        viewAdapter= FloorAdapter(this)
        //        建立database實例
        database = Firebase.database.reference
        //        realtime監聽
        val listener=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                接收廣播
                try {
                    val res=snapshot.value as HashMap<*,*>
                    val nodeList=res[machineType] as HashMap<*,*>
//                建立recyclerView
                    setRecyclerView(nodeList,'C')
                }catch (e: Exception){
                    Log.d("firebase",e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                接收廣播失敗
            }

        }
//        新增監聽事件
        database.addValueEventListener(listener)
    }


    private fun setRecyclerView(adapterData:HashMap<*,*>,building:Char){
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.BuildC.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = viewAdapter
        }
        viewAdapter.data=adapterData
        viewAdapter.building=building
    }

//    元素點擊事件
    override fun onItemClick(position: Int) {
        Intent(requireContext(),MachineStatus::class.java).apply {
            putExtra("DataType",machineType)
            putExtra("floor","C-0${position+1}")
            startActivity(this)
        }
    }


}