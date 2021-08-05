package com.example.tkulife_pro.admin.packageManagement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.PackageAdminItemBinding
import com.example.tkulife_pro.databinding.PackageItemBinding
import com.example.tkulife_pro.student.stdPackage.PackageAdapter
import org.json.JSONArray
import org.json.JSONObject

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private lateinit var binding : PackageAdminItemBinding
    lateinit var packageList : JSONArray
    class ViewHolder(val view: PackageAdminItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= PackageAdminItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pkg = packageList[position] as JSONObject
        holder.view.textView37.text = "編號末三碼: ${pkg["pid"]}"
        holder.view.textView39.text="已領取"
    }

    override fun getItemCount(): Int {
        return packageList.length()
    }
}
