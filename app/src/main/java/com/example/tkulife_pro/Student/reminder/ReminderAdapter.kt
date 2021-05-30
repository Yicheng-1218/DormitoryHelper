package com.example.tkulife_pro.Student.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.ReminderItemBinding


class ReminderAdapter(private var itemClickListener:OnItemClick):
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    var dataSet= ArrayList<Array<Int>>()
    private val hour=0
    private val minute=1


    class ViewHolder(val view: ReminderItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding =
            ReminderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //接收sqlite傳入參數&邏輯判斷
        var hour=dataSet[position][hour]
        val minute=dataSet[position][minute]
        if (hour>=12){
            holder.view.textView10.text="下午"
            if (hour>12)  hour-=12
        }else{
            holder.view.textView10.text="上午"
        }
        if (minute<10){
            holder.view.textView11.text="$hour:0$minute"
        }else{
            holder.view.textView11.text="$hour:$minute"
        }

        holder.view.layoutItem.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}

