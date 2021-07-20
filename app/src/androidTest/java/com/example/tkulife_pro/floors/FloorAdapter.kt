package com.example.tkulife_pro.floors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.FloorItemBinding
import com.example.tkulife_pro.student.reminder.ReminderAdapter
import kotlin.math.floor
import kotlin.properties.Delegates


class FloorAdapter(private var itemClickListener: OnItemClick): RecyclerView.Adapter<FloorAdapter.ViewHolder>(){

    lateinit var  data: HashMap<*, *>
    var building by Delegates.notNull<Char>()

    class ViewHolder(val view:FloorItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res=getUsableCount(data,building)
        val floor=position+1
        holder.view.textView22.text = "${"$building-0$floor"} 層樓有:"
        holder.view.textView23.text = "${res["$building-0$floor"]}台可用"
        holder.view.floorItem.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }
    override fun getItemCount(): Int {
        return 6
    }


    //    取得每樓層可用機器數量
    private fun getUsableCount(data:HashMap<*,*>,building:Char):MutableMap<String,Int>{
        val res= mutableMapOf<String,Int>()
        for (key in data.keys){
            var count=0
            key as String
            val subKey=key[0]
            if (subKey==building){
                for (field in data[key] as ArrayList<Map<*,*>>){
                    if (field["con"]=="usable"){
                        count++
                    }
                }
            }
            res[key]=count
        }
        return res
    }

//    時做onClick
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}