package com.example.tkulife_pro.admin.fixreport.FixTab

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.FixItemBinding
import org.json.JSONArray
import org.json.JSONObject

class PageAdapter(private var itemClickListener: OnItemClick): RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    lateinit var  data: JSONArray

    class ViewHolder(val view: FixItemBinding) :RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FixItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val details=data[position] as JSONObject
        val location=details["location"].toString()
        val type=location.split('_')
        holder.view.textView27.text = "${type[1]}-${(type[2].toInt()+1)}"
        var machine=type[0]
        when (machine){
            "Washer" -> machine = "洗衣機"
            "Dryer" -> machine = "烘衣機"
        }
        holder.view.textView29.text = machine

        holder.view.fixItem.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return data.length()
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}