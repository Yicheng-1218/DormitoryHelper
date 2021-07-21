package com.example.tkulife_pro.student.laundry.status.machineStatus

import android.os.Bundle
import android.sax.StartElementListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.FragmentBuilding3Binding
import com.example.tkulife_pro.student.laundry.status.SharedViewModel

class Building3 : Fragment() {

    private lateinit var binding: FragmentBuilding3Binding
    private lateinit var viewModel: SharedViewModel
    private lateinit var viewAdapter: StatusAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBuilding3Binding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView(){
        viewAdapter=StatusAdapter()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.getRealtimeData().observe(viewLifecycleOwner,{ data->
//            TODO UPDATE UI ON HERE

        })
    }

}