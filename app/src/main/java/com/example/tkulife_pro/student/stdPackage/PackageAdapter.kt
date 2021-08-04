package com.example.tkulife_pro.student.stdPackage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.PackageItemBinding
import org.json.JSONArray
import org.json.JSONObject

class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>(){
    private lateinit var binding : PackageItemBinding
    lateinit var packageList : JSONArray
    class ViewHolder(val view:PackageItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = PackageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pkg = packageList[position] as JSONObject
        holder.view.textView32.text = "編號末三碼: ${pkg["pid"]}"
    }

    override fun getItemCount(): Int {
        return packageList.length()
    }

}