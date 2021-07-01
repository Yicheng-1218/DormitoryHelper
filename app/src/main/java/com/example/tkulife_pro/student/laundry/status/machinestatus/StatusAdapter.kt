package com.example.tkulife_pro.student.laundry.status.machinestatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.MachineItemBinding

class StatusAdapter: RecyclerView.Adapter<StatusAdapter.ViewHolder>(){
    lateinit var dataList:ArrayList<Map<*,*>>


    class ViewHolder(val view:MachineItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding=MachineItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=dataList[position]
        holder.view.textView14.text=data["id"].toString()
        holder.view.textView16.text=data["con"].toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}