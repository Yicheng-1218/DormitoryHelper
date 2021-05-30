package com.example.tkulife_pro.Student.reminder

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.Sqlite
import com.example.tkulife_pro.databinding.ActivityPushNotificationBinding
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList


class PushNotification : AppCompatActivity(),ReminderAdapter.OnItemClick {
    private lateinit var binding: ActivityPushNotificationBinding
    private val viewAdapter=ReminderAdapter(this)
    private val hour=0
    private val minute=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
        binding.button4.setOnClickListener {
            val c = Calendar.getInstance()
            var hour_now = c.get(Calendar.HOUR_OF_DAY)
            var minute_now = c.get(Calendar.MINUTE)
            TimePickerDialog(this,{
                    _,hour,minute->
                        val data= arrayOf(hour, minute)
                        addSQLTimer(data)
                        setRecyclerView(getSQLTimer())

            },hour_now,minute_now,false).show()
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
            adapter=viewAdapter
        }
        viewAdapter.dataSet=getSQLTimer()
    }
    private fun getSQLTimer(): ArrayList<Array<Int>> {
        //get sqlite table time
        val db=Sqlite(this)
        return db.getTimer()
    }
    private fun addSQLTimer(time:Array<Int>){
        //set sqlite table time
        val db=Sqlite(this)
        db.addTimer(time)
    }

    override fun onItemClick(position: Int) {
        val timerList=getSQLTimer()[position]
        println(position)
        val old_hour=timerList[hour]
        val old_minute=timerList[minute]
        fun updateTimer(){
            TimePickerDialog(this,{
                    _,hour,minute->
                Sqlite(this).updateTimer(old_hour,old_minute,hour,minute)
                setRecyclerView(getSQLTimer())
            },timerList[hour],timerList[minute],false).show()

        }

        val alter=AlertDialog.Builder(this)
        alter.setTitle("提示")
        alter.setMessage("選擇修改或是刪除提醒")
        alter.setPositiveButton("修改"
        ) { _, _ -> updateTimer() }
        alter.setNegativeButton("刪除"
        ) { _, _ -> Sqlite(this).delTimer(old_hour,old_minute);setRecyclerView(getSQLTimer()) }
        alter.setNeutralButton("取消",null)
        alter.show()

    }
//    TODO 定時提醒實作
}