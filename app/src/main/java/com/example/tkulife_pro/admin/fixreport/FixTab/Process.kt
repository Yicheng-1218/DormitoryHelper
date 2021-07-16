package com.example.tkulife_pro.admin.fixreport.FixTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkulife_pro.R
import com.example.tkulife_pro.admin.fixreport.FixNotification
import com.example.tkulife_pro.databinding.FragmentCheckBinding
import com.example.tkulife_pro.databinding.FragmentProcessBinding


class Process(private val machineType:String) : Fragment(),TabAdapter.OnItemClick {
    private lateinit var binding: FragmentProcessBinding
    private lateinit var viewAdapter: PageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProcessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onItemClick(position: Int) {
        Intent(requireContext(),FixNotification::class.java).apply {
            startActivity(this)
        }
    }

}