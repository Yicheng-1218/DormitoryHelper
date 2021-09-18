package com.tkuLife.dorm.student.laundry.status.floor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tkuLife.dorm.databinding.FloorItemBinding

class FloorAdapter(private var itemClickListener: OnItemClick): RecyclerView.Adapter<FloorAdapter.ViewHolder>() {

    var machineData = emptyArray<Int>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    lateinit var machineType:String

    class ViewHolder(val view: FloorItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView22.text = "${position+1}F"
        holder.view.textView23.text = "共有${machineData[position]}台可用"
        holder.view.floorItem.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return machineData.size
    }

//    拆解資料計算可用台數


//    元素點擊介面
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}