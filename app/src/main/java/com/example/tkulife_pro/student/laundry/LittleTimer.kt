package com.example.tkulife_pro.student.laundry

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityLittleTimerBinding
import com.example.tkulife_pro.TkuNotification
import java.util.*
import kotlin.properties.Delegates


class LittleTimer : AppCompatActivity() {
    private lateinit var binding:ActivityLittleTimerBinding
    private lateinit var hourEditText:EditText
    private lateinit var minuteEditText:EditText
    private lateinit var secondEditText:EditText
    private var hour by Delegates.notNull<String>()
    private var minute by Delegates.notNull<String>()
    private var second by Delegates.notNull<String>()
    private var timerRunning=false
    private var timeLeftInMilliSecond:Long = 0
    private lateinit var myTimer:CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLittleTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    @SuppressLint("ResourceAsColor")
    private fun initView(){
//        設定BAR
        BarTool(this).setBundle("計時提醒",R.color.barBlue)
//        初始化editText
        hourEditText=binding.textInputLayout2.editText!!
        minuteEditText=binding.textInputLayout.editText!!
        secondEditText=binding.textInputLayout3.editText!!
        hourEditText.setText("0")
        minuteEditText.setText("0")
        secondEditText.setText("0")


//        重設
        binding.button8.setOnClickListener {
            hourEditText.setText("0")
            minuteEditText.setText("0")
            secondEditText.setText("0")
            currentFocus?.clearFocus()
            if (timerRunning&&timeLeftInMilliSecond>0L){
                binding.button8.text="重設"
                timeLeftInMilliSecond=0
                myTimer.onFinish()
                startStop()
            }

        }

//        開始計時
        binding.button9.setOnClickListener {
            timeLeftInMilliSecond=getMillis()
            if(timeLeftInMilliSecond>0L){
                Log.d("timer","millieSecond:$timeLeftInMilliSecond")
                startStop()
                currentFocus?.clearFocus()
            }
        }


    }


//    更新時間方法
    private fun updateTimer(){
        val hour=timeLeftInMilliSecond.toInt()/(60000*60)
        val minute=timeLeftInMilliSecond.toInt()/60000
        val second=timeLeftInMilliSecond.toInt() % 60000 / 1000
        hourEditText.setText(hour.toString())
        minuteEditText.setText(minute.toString())
        secondEditText.setText(second.toString())
    }

//    計時開關
    private fun startStop(){
        if (timerRunning){
            stopTimer()
        }else{
            startTimer()
        }
    }

//    暫停計時
    private fun stopTimer(){
        myTimer.cancel()
        binding.button9.text="繼續"
        timerRunning=false
    }

//    開始計時
    @SuppressLint("ResourceAsColor")
    private fun startTimer(){
        myTimer=object:CountDownTimer(timeLeftInMilliSecond,1000){
            override fun onTick(p0: Long) {
                timeLeftInMilliSecond=p0
                updateTimer()
                hourEditText.isEnabled=false
                minuteEditText.isEnabled=false
                secondEditText.isEnabled=false
            }

            override fun onFinish() {
                TkuNotification(this@LittleTimer,"洗衣計時","洗衣計時").build("計時提醒","快去拿衣服啦(⁎⁍̴̛ᴗ⁍̴̛⁎)").show(
                    R.string.littleTimer
                )
                hourEditText.isEnabled=true
                minuteEditText.isEnabled=true
                secondEditText.isEnabled=true
                binding.button8.text="重設"
                binding.button9.apply {
                    text="開始計時" }
            }
        }.start()
        binding.button9.apply {
            text="暫停" }
        binding.button8.text="取消"
        timerRunning=true
    }

//    取得millis時間
    private fun getMillis(): Long {
        var millis:Long=0
        hour= hourEditText.text.toString()
        minute= minuteEditText.text.toString()
        second= secondEditText.text.toString()
        if(hour=="0"&&minute=="0"&&second=="0"){
            secondEditText.error="不可設定0秒鬧鐘"
        }else{
            if (hour!="0"){
                millis+=hour.toLong()*1000*60*60
            }
            if (minute!="0"){
                millis+=minute.toLong()*1000*60
            }
            if (second!="0"){
                millis+=second.toLong()*1000
            }
        }
        return millis
    }
}