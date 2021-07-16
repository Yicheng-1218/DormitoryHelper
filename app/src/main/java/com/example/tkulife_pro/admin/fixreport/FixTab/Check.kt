package com.example.tkulife_pro.admin.fixreport.FixTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkulife_pro.R
import com.example.tkulife_pro.admin.fixreport.FixNotification
import com.example.tkulife_pro.databinding.FragmentBuildingABinding
import com.example.tkulife_pro.databinding.FragmentCheckBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Check(private val machineType:String) : Fragment(),TabAdapter.OnItemClick {
    private lateinit var binding: FragmentCheckBinding
    private lateinit var viewAdapter: PageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckBinding.inflate(layoutInflater)
        //initView()
        return binding.root
    }
//    private fun initView(){
//        viewAdapter = PageAdapter(this)
//        val listener = object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                try {
//                    val res=snapshot.value as HashMap<*,*>
//                    val nodeList=res[machineType] as HashMap<*,*>
//                    setRecyclerView(nodeList,'')
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        }
//    }
//
//    private fun setRecyclerView(adapterData:HashMap<*,*>,)

    override fun onItemClick(position: Int) {
        Intent(requireContext(),FixNotification::class.java).apply{
            startActivity((this))
        }
    }


}