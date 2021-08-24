package com.example.tkulife_pro.student.laundry.status.machineStatus

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.R
import com.example.tkulife_pro.SharedXML
import com.example.tkulife_pro.databinding.MachineItemBinding
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class StatusAdapter:RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    lateinit var floor :String
    lateinit var machineData : ArrayList<HashMap<*,*>>
    lateinit var machineType : String
    lateinit var sharedXML: SharedXML
    val timerMap = mapOf<String,Int>("A" to 1620000, "B" to 2400000, "C" to 4080000)

    class ViewHolder(val view: MachineItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ViewHolder {
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
        val num = "${floor}-${machine["id"]}"
        holder.view.textView14.text = num

        when(machine["con"]){
            "using"->{
                holder.view.textView16.apply {
                    text = "運轉"
                    setTextColor(Color.parseColor("#ff9900"))
                }
                if(::sharedXML.isInitialized){
                    val countingMap = sharedXML.getXML("countingMap")
                    var counting = countingMap!!.getBoolean(num,false)
                    if(!counting){
                        val millisec = getTime(machine)
                        if (millisec != null) {
                            object : CountDownTimer(millisec.toLong(), 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    countingMap.edit().putBoolean(num,true)
                                    var min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                    var sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                                    holder.view.textView64.text = "${min}:${sec}"
                                }
                                override fun onFinish() {
                                    countingMap.edit().putBoolean(num,false)
                                }
                            }.start()
                        }
                    }
                }
            }
            "usable"->{
                holder.view.textView16.apply {
                    text =  "閒置"
                    setTextColor(Color.parseColor("#3369e7"))
                }
                holder.view.textView64.apply {
                    visibility = View.INVISIBLE
                    height = 5
                }
            }
            "broken"->{
                holder.view.textView16.apply {
                    text = "故障"
                    setTextColor(Color.parseColor("#ed1c24"))
                }
                holder.view.textView64.apply {
                    visibility = View.INVISIBLE
                    height = 5
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return machineData.size
    }

    private fun getTime(machine:HashMap<*,*>):Int?{
        var mode = listOf<String>()
        try{
            mode = machine["mode"] as List<String>
            return (timerMap[mode[0]]!! +timerMap[mode[1]]!!)/2
        }catch (E:Exception){
            return null
        }
    }
}