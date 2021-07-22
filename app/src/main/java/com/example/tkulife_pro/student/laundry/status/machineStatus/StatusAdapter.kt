package com.example.tkulife_pro.student.laundry.status.machineStatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.MachineItemBinding

class StatusAdapter:RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    lateinit var floor :String
    lateinit var machineData : ArrayList<HashMap<*,*>>
    lateinit var machineType : String

    class ViewHolder(val view: MachineItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = MachineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(machineType=="Washer"){
            holder.view.imageView4.setImageResource(R.drawable.ic_washing_machine)
        }else{
            holder.view.imageView4.setImageResource(R.drawable.ic_tumble_dryer)
        }
        var con =""
        val machine = machineData[position]
        holder.view.textView14.text = "${floor}-${machine["id"]}"
        when (machine["con"]){
            "using" -> con="運轉"
            "usable" -> con="閒置"
            "broken" -> con="故障"
        }

        holder.view.textView16.text = con
    }

    override fun getItemCount(): Int {
        return machineData.size
    }

}