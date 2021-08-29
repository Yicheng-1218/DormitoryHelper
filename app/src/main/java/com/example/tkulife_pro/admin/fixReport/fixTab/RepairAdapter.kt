package com.example.tkulife_pro.admin.fixReport.fixTab

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.FixItemBinding
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class RepairAdapter(private var itemClickListener: OnItemClick): RecyclerView.Adapter<RepairAdapter.ViewHolder>() {

    var  data: JSONArray= JSONArray()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: FixItemBinding) :RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FixItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val details=data[position] as JSONObject
        val location=details["location"].toString()
//        location路徑分割
        val type=location.split('_')
        var mid=""
//        字串轉換對照表
        if(type[1]!="1F"){
//            分館分樓報修顯示
            val ref= mapOf(
                "A" to "一館",
                "B" to "二館",
                "C" to "三館",
                "02" to "2F",
                "03" to "3F",
                "04" to "4F",
                "05" to "5F",
                "06" to "6F")
            var floor= listOf<String>()
            try{
                floor = type[1].split('-')
                mid="${ref[floor[0]]}-${ref[floor[1]]}-${(type[2].toInt()+1)}"
                holder.view.textView27.text = mid
            }catch(E:Exception){
                Log.d("index",floor.toString())
            }
        }else{
//            1F報修顯示
            mid="${type[1]}-${type[2].toInt()+1}"
            holder.view.textView27.text = mid
        }


//        取得機器種類
        var machine=type[0]
        when (machine){
            "Washer" -> {
                machine = "洗衣機"
                holder.view.imageView5.setImageResource(R.drawable.ic_washericon_new)
            }
            "Dryer" -> {
                machine = "烘衣機"
                holder.view.imageView5.setImageResource(R.drawable.ic_dryer_newicon)
            }
        }
//        顯示機器種類
        holder.view.textView29.text = machine

//        點擊回傳position
        holder.view.fixItem.setOnClickListener {
            itemClickListener.onItemClick(position,"【$machine-$mid】")
        }
    }

    override fun getItemCount(): Int {
        return data.length()
    }

    interface OnItemClick{
        fun onItemClick(position: Int,id:String)
    }
}