package com.example.tkulife_pro.student.laundry.status.machineStatus

import android.annotation.SuppressLint
import android.graphics.Color
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        對照機器種類給予圖片
        if(machineType=="Washer"){
            holder.view.imageView4.setImageResource(R.drawable.ic_washing_machine)
        }else{
            holder.view.imageView4.setImageResource(R.drawable.ic_tumble_dryer)
        }


        val machine = machineData[position]

//        編號
        holder.view.textView14.text = "${floor}-${machine["id"]}"

        when(machine["con"]){
            "using"->{
                holder.view.textView16.apply {
                    text = "運轉"
                    setTextColor(Color.parseColor("#ff9900"))
                }
            }
            "usable"->{
                holder.view.textView16.apply {
                    text =  "閒置"
                    setTextColor(Color.parseColor("#3369e7"))
                }
            }
            "broken"->{
                holder.view.textView16.apply {
                    text = "故障"
                    setTextColor(Color.parseColor("#ed1c24"))
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return machineData.size
    }

}