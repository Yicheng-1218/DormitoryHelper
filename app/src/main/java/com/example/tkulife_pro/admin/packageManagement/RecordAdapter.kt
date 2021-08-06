package com.example.tkulife_pro.admin.packageManagement

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.PackageItemBinding
import org.json.JSONArray
import org.json.JSONObject

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private lateinit var binding : PackageItemBinding
    lateinit var packageList : ArrayList<HashMap<*,*>>
    class ViewHolder(val view: PackageItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= PackageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pkg = packageList[position]
        val map= mapOf(
            true to "已領",
            false to "未領"
        )
        holder.view.textView32.text
        holder.view.textView33.text=map[pkg["taken"]]

    }

    override fun getItemCount(): Int {
        return packageList.size
    }
}
