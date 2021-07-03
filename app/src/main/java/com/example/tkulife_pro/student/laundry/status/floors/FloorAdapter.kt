package com.example.tkulife_pro.student.laundry.status.floors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.FloorItemBinding
import com.example.tkulife_pro.databinding.MachineItemBinding
import com.example.tkulife_pro.student.laundry.status.machinestatus.StatusAdapter

class FloorAdapter: RecyclerView.Adapter<FloorAdapter.ViewHolder>(){

    class ViewHolder(val view:FloorItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorAdapter.ViewHolder {
        val itemBinding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView22.text = "該層樓有:"
        holder.view.textView23.text = "10台可用"
    }
    override fun getItemCount(): Int {
        return 0
    }

}