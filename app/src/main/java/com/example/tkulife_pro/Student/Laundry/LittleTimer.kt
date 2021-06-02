package com.example.tkulife_pro.Student.Laundry

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.tkulife_pro.Student.Reminder.PushNotification
import com.example.tkulife_pro.databinding.ActivityLittleTimerBinding
import com.example.tkulife_pro.tkuNotification
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.math.min
import kotlin.properties.Delegates
import kotlin.time.milliseconds

class LittleTimer : AppCompatActivity() {
    private lateinit var binding:ActivityLittleTimerBinding
    private lateinit var hourEditText:EditText
    private lateinit var minuteEditText:EditText
    private lateinit var secondEditText:EditText
    private var hour by Delegates.notNull<String>()
    private var minute by Delegates.notNull<String>()
    private var second by Delegates.notNull<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLittleTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        hourEditText=binding.textInputLayout2.editText!!
        minuteEditText=binding.textInputLayout.editText!!
        secondEditText=binding.textInputLayout3.editText!!

        binding.button.setOnClickListener {
            super.onBackPressed()
        }
        binding.button8.setOnClickListener {
            hourEditText?.setText("")
            minuteEditText?.setText("")
            secondEditText?.setText("")
            clearFocus()
        }
        binding.button9.setOnClickListener {
            val millis=getMillis()
            if(millis!=0L){
                setTimer(millis)
                Log.d("timer", "h: $hour")
                Log.d("timer","m: $minute")
                Log.d("timer","s: $second")
                Log.d("timer", "millis: $millis")
//                TODO 實作取消鬧鐘方法
                Snackbar.make(it,"計時器已設定",Snackbar.LENGTH_LONG).show()
            }
            hourEditText?.setText("")
            minuteEditText?.setText("")
            secondEditText?.setText("")
            clearFocus()


        }
    }
    private fun clearFocus(){
        hourEditText?.clearFocus()
        minuteEditText?.clearFocus()
        secondEditText?.clearFocus()
    }

    private fun setTimer(millis:Long){
        var alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(this, AlarmReceiver::class.java)
        intent.action="littleTimer"
        val pendingIntent= PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("timer","Create: "+Date().toString())
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+millis,pendingIntent)
    }

    private fun getMillis(): Long {
        var millis:Long=0
        hour=hourEditText?.text.toString()
        minute=minuteEditText?.text.toString()
        second=secondEditText?.text.toString()
        if(hour.isEmpty()&&minute.isEmpty()&&second.isEmpty()){
            secondEditText.error="不可完全空白"
        }
        if (hour.isNotEmpty()){
            millis+=hour.toLong()*1000*60*60
        }
        if (minute.isNotEmpty()){
            millis+=minute.toLong()*1000*60
        }
        if (second.isNotEmpty()){
            millis+=second.toLong()*1000
        }
        return millis
    }

    class AlarmReceiver:BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("timer", "Receiver: " + Date().toString())
            tkuNotification(p0!!,"洗衣提醒","洗衣提醒").build("計時器","設定的計時器到啦(⁎⁍̴̛ᴗ⁍̴̛⁎)").show(1)
        }

    }
}