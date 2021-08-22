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

    lateinit var  data: JSONArray

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
                holder.view.textView27.text = "${ref[floor[0]]}-${ref[floor[1]]}-${(type[2].toInt()+1)}"
            }catch(E:Exception){
                Log.d("index",floor.toString())
            }
        }else{
//            1F報修顯示
            holder.view.textView27.text = "     ${type[1]}-${type[2].toInt()+1}     "
        }


//        取得機器種類
        var machine=type[0]
        when (machine){
            "Washer" -> {
                machine = "洗衣機"
                holder.view.imageView5.setImageResource(R.drawable.ic_washing_machine)
            }
            "Dryer" -> {
                machine = "烘衣機"
                holder.view.imageView5.setImageResource(R.drawable.ic_tumble_dryer)
            }
        }
//        顯示機器種類
        holder.view.textView29.text = machine

//        點擊回傳position
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