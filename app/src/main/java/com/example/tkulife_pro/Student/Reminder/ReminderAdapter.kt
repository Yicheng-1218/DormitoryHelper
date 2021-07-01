package com.example.tkulife_pro.Student.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.R

//TODO 修正建構子傳入參數為sqlite格式
class ReminderAdapter(private val dataSet: ArrayList<Array<Int>>):
    RecyclerView.Adapter<ReminderAdapter.viewholder>() {
    private val hour=0
    private val minute=1
    class viewholder(view: View):RecyclerView.ViewHolder(view){
        val ampm: TextView
        val time: TextView
        init {
            ampm=view.findViewById(R.id.textView10)
            time=view.findViewById(R.id.textView11)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.reminder_item,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        //接收sqlite傳入參數&邏輯判斷
        var hour=dataSet[position][hour]
        val minute=dataSet[position][minute]
        if (hour>=12){
            holder.ampm.text="下午"
            if (hour>12)  hour-=12
        }else{
            holder.ampm.text="上午"
        }
        if (minute==0){
            holder.time.text="$hour:00"
        }else{
            holder.time.text="$hour:$minute"
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

