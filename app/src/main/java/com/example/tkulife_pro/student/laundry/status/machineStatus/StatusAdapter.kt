package com.example.tkulife_pro.student.laundry.status.machineStatus

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.R
import com.example.tkulife_pro.SharedXML
import com.example.tkulife_pro.databinding.MachineItemBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class StatusAdapter(val context: Context):RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    var floor =""
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    var machineData = ArrayList<HashMap<*,*>>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    var machineType =""
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    class ViewHolder(val view: MachineItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = MachineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        對照機器種類給予圖片
        if(machineType=="Washer"){
            holder.view.imageView4.setImageResource(R.drawable.ic_washericon_new)
        }else{
            holder.view.imageView4.setImageResource(R.drawable.ic_dryer_newicon)
        }


        val machine = machineData[position]


//        編號
        val num = "${floor}-${machine["id"]}"
        holder.view.textView14.text = num

        when(machine["con"]){
            "using"->{
                holder.view.textView16.apply {
                    text = try {
                        "預計完成時間:\n${timeFormat(machine["finish"] as Long)}"
                    }catch (e:Exception){
                        Log.e("time",e.toString())
                        "預計完成時間:\n計算中"
                    }
                    setTextColor(Color.parseColor("#BF4E30"))
                }
                try {
                    if (System.currentTimeMillis() <= machine["finish"] as Long){
                        busyBroadcast(machine["finish"] as Long,position,num)
                    }
                }catch (e:Exception){
                    Log.d("busy",e.toString())
                }
            }
            "usable"->{
                holder.view.textView16.apply {
                    text =  "閒置"
                    setTextColor(Color.parseColor("#157F1F"))
                }
            }
            "broken"->{
                holder.view.textView16.apply {
                    text = "故障"
                    setTextColor(Color.parseColor("#FAA300"))
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return machineData.size
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeFormat(milliSeconds:Long):String?{
        return SimpleDateFormat("HH:mm").format(milliSeconds)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun busyBroadcast(milliSeconds: Long,position: Int,num:String){
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent=Intent(context,Receiver::class.java).apply {
            putExtra("pos",position)
            putExtra("num",num)
            putExtra("type",machineType)
            action="busy"
        }
        val pi=PendingIntent.getBroadcast(context,position+1218, intent,PendingIntent.FLAG_ONE_SHOT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, milliSeconds,pi)
        Log.d("busyBroadcast","Broadcast set")
    }

    class Receiver: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val num=p1?.getStringExtra("num")?.split("-")
            val type=p1?.getStringExtra("type")
            try {
                Log.d("busyReceive","onReceive")
                Firebase.database.getReference(type!!).child(num!![0]).child("${num[1].toInt()-1}").get().addOnSuccessListener {
                    val machine =it.value as HashMap<*,*>
                    if (machine["con"]=="using"){
                        val upDataData= mapOf(
                            "finish" to System.currentTimeMillis()+(1000*60*5)
                        )
                        Firebase.database.getReference(type).child(num!![0]).child("${num[1].toInt()-1}").updateChildren(upDataData)
                    }
                }

            }catch (e:Exception){

            }
        }

    }

}