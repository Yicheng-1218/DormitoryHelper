package com.example.tkulife_pro.admin.fixreport.FixTab

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.FixItemBinding

class PageAdapter(private var itemClickListener: OnItemClick): RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    lateinit var  data: HashMap<*, *>

    class ViewHolder(val view: FixItemBinding) :RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FixItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView27.text = "A-2F-01"
        holder.view.textView28.text = ">"
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