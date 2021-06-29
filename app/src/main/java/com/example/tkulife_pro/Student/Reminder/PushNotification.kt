package com.example.tkulife_pro.Student.Reminder

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tkulife_pro.R
import com.example.tkulife_pro.SharedXML
import com.example.tkulife_pro.Sqlite
import com.example.tkulife_pro.databinding.ActivityPushNotificationBinding
import com.example.tkulife_pro.tkuNotification
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class PushNotification : AppCompatActivity(),ReminderAdapter.OnItemClick {
    private lateinit var binding: ActivityPushNotificationBinding
    private val viewAdapter=ReminderAdapter(this)
    private val hour=0
    private val minute=1

    var trashReminder by Delegates.notNull<Boolean>()
    var packageReminder by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
//        取得開關狀態表
        val timerXML = SharedXML(this).getXML("timer")!!

//        設定鬧鐘紐
        binding.button4.setOnClickListener {
            val c = Calendar.getInstance()
            val hourNow = c.get(Calendar.HOUR_OF_DAY)
            val minuteNow = c.get(Calendar.MINUTE)
            TimePickerDialog(this, { _, hour, minute ->
                val data = arrayOf(hour, minute, System.currentTimeMillis().toInt())
                addSQLTimer(data)
                setRecyclerView(getSQLTimer())

            }, hourNow, minuteNow, false).show()
        }
//        初始化RecyclerView
        setRecyclerView(getSQLTimer())

//        返回鍵
        binding.button5.setOnClickListener {
            super.onBackPressed()
        }

//        垃圾車提醒開關
        binding.switch1.apply {
            isChecked = timerXML.getBoolean("trashReminder", isChecked)
            trashReminder = isChecked
            setOnCheckedChangeListener { _, b ->
                trashReminder = isChecked
                timerXML.edit().putBoolean("trashReminder", b).apply()
                isChecked = timerXML.getBoolean("trashReminder", isChecked)
            }

        }

//        包裹提醒開關
        binding.switch2.apply {
            isChecked = timerXML.getBoolean("packageReminder", isChecked)
            packageReminder = isChecked
            setOnCheckedChangeListener { _, b ->
                packageReminder = isChecked
                timerXML.edit().putBoolean("packageReminder", b).apply()
                isChecked = timerXML.getBoolean("packageReminder", isChecked)
            }

        }
        binding.textView12.setOnClickListener {
//            測試刪除TABLE
            Sqlite(this).dropTable()
            recreate()
        }

    }

//    設定RecyclerView內容
    private fun setRecyclerView(data: ArrayList<Array<Int>>){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.RecyclerView.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
//            設定分隔線
            addItemDecoration(
                DividerItemDecoration(
                    this@PushNotification,
                    DividerItemDecoration.VERTICAL
                )
            )

            adapter=viewAdapter
        }
        //設定傳入recyclerview參數
        viewAdapter.dataSet=data
    }

//    取得SQL資料
    private fun getSQLTimer(): ArrayList<Array<Int>> {
        //get sqlite table time
        val db=Sqlite(this)
        return db.getTimer()
    }

//    添加SQL資料
    private fun addSQLTimer(time:Array<Int>){
        //set sqlite table time
        val db=Sqlite(this)
        db.addTimer(time)
    }

//    覆寫RecyclerView的點擊監聽
    override fun onItemClick(position: Int) {
        val timerList=getSQLTimer()[position]
        println(position)
        val createAt=timerList[2]
        fun updateTimer(){
            TimePickerDialog(this,{
                    _,hour,minute->
                Sqlite(this).updateTimer(createAt,hour,minute)
                setRecyclerView(getSQLTimer())
            },timerList[hour],timerList[minute],false).show()

        }

        val alter=AlertDialog.Builder(this)
        alter.setTitle("提示")
        alter.setMessage("選擇修改或是刪除提醒")
        alter.setPositiveButton("修改"
        ) { _, _ -> updateTimer() }
        alter.setNegativeButton("刪除"
        ) { _, _ -> Sqlite(this).delTimer(createAt);setRecyclerView(getSQLTimer()) }
        alter.setNeutralButton("取消",null)
        alter.show()

    }

//    添加循環鬧鐘
    private fun addAlarm(createAT:Int){
//    TODO 設定循環鬧鐘的trigger
        val alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(this,TrashReceiver::class.java)
        intent.action="trash"
        intent.addCategory(createAT.toString())
        val pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("timer","Create: "+Date().toString())
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,86400000,pendingIntent)
    }
//    清除循環鬧鐘
    private fun cancelAlarm(createAT: Int){
        val alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(this,TrashReceiver::class.java)
        intent.action="trash"
        intent.addCategory(createAT.toString())
        val pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

//    廣播接收
    class TrashReceiver:BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val timerXML= SharedXML(p0!!).getXML("timer")
            val status=timerXML?.getBoolean("trashReminder",false)
            if (status == true) {
//                發送提醒
                tkuNotification(p0, "垃圾車提醒", "垃圾車提醒").build("垃圾車提醒", "該倒垃圾啦(⁎⁍̴̛ᴗ⁍̴̛⁎)").show(R.string.trashReminder)
                val wakelock = p0.getSystemService(POWER_SERVICE) as PowerManager
//                兩個Flag缺一不可，都存在才可以點亮螢幕
                val newWake = wakelock.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_DIM_WAKE_LOCK,
                    "MyApp::trash"
                )
//                自動熄滅時間
                newWake.acquire(5000 /*5 second*/)
            }
        }
    }
}