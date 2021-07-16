package com.example.tkulife_pro.admin.fixreport.FixTab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.FixItemBinding
import com.example.tkulife_pro.student.laundry.status.floors.FloorAdapter

class PageAdapter(private val itemClickListener: FloorAdapter.OnItemClick): RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    lateinit var dataList:ArrayList<Map<*,*>>

    class ViewHolder(val view: FixItemBinding) :RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = FixItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=dataList[position]
        holder.view.textView27.text=data["id"].toString()
        holder.view.fixItem.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}