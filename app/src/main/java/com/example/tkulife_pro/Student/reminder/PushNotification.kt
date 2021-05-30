package com.example.tkulife_pro.Student.reminder

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.Sqlite
import com.example.tkulife_pro.databinding.ActivityPushNotificationBinding
import java.util.*
import kotlin.collections.ArrayList


class PushNotification : AppCompatActivity() {
    private lateinit var binding: ActivityPushNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        binding.button4.setOnClickListener {
            val c = Calendar.getInstance()
            var hour = c.get(Calendar.HOUR_OF_DAY)
            var minute = c.get(Calendar.MINUTE)
            TimePickerDialog(this,{
                    _,hour,minute->
                        val data= arrayOf(hour, minute)
                        setSQLTimer(data)
                        setRecyclerView(getSQLTimer())

            },hour,minute,false).show()
        }

        setRecyclerView(getSQLTimer())
        binding.button5.setOnClickListener{
            super.onBackPressed()
        }
    }
    private fun setRecyclerView(data: ArrayList<Array<Int>>){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.RecyclerView.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            addItemDecoration(
                DividerItemDecoration(
                    this@PushNotification,
                    DividerItemDecoration.VERTICAL
                )
            )
            //設定傳入recyclerview參數
            adapter=ReminderAdapter(data)
        }
    }
    private fun getSQLTimer(): ArrayList<Array<Int>> {
        //get sqlite table time
        val db=Sqlite(this)
        return db.getTimer()
    }
    private fun setSQLTimer(time:Array<Int>){
        //set sqlite table time
        val db=Sqlite(this)
        db.addTimer(time)
    }
//    TODO SQL刪除timer
//    TODO 定時提醒實作
}