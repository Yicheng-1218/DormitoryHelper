package com.example.tkulife_pro.admin.fixreport.FixTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.admin.fixreport.FixNotification
import com.example.tkulife_pro.databinding.FragmentProcessBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class Process(private val machineType:String) : Fragment(),PageAdapter.OnItemClick {
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

    private fun initView(){
        viewAdapter = PageAdapter(this)
        val listener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val res = snapshot.value as HashMap<*,*>
                val nodeList = res[machineType] as HashMap<*,*>
                setRecyclerView(nodeList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

    }

    private fun setRecyclerView(adapterData: HashMap<*,*>) {
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
        Intent(requireContext(),FixNotification::class.java).apply {
            startActivity(this)
        }
    }

}