package com.tkuLife.dorm.student.reminder

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.tkuLife.dorm.*
import com.tkuLife.dorm.databinding.ActivityPushNotificationBinding
import com.tkuLife.dorm.databinding.GarbageTruckTimeBinding
import java.util.*
import kotlin.collections.ArrayList


class PushNotification : AppCompatActivity(){
    private lateinit var binding: ActivityPushNotificationBinding
    private lateinit var trashIntent:Intent
//    private val viewAdapter=ReminderAdapter(this)
    private val timeList= listOf(
        "18:55",
        "18:50",
        "18:45",
        "18:30",
        "18:00"
    )
    private val requestCode= arrayOf(1855,1850,1845,1830,1800)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initView() {
//        設定BAR
        BarTool(this).setBundle("垃圾車提醒",R.color.barBlue)
//        取得開關狀態表
//        val timerXML = SharedXML(this).getXML("timer")!!


//        到垃圾廣播意圖
        trashIntent=Intent(this,TrashReceiver::class.java)
        trashIntent.action="trash"


//        設定鬧鐘紐
        binding.button4.setOnClickListener {
//            val c = Calendar.getInstance()
//            val hourNow = c.get(Calendar.HOUR_OF_DAY)
//            val minuteNow = c.get(Calendar.MINUTE)
//            TimePickerDialog(this, { _, hour, minute ->
//                val data = arrayOf(hour, minute, System.currentTimeMillis().toInt())
//                addSQLTimer(data)
//                upDateRecycler(getSQLTimer())
//
////              設定循環鬧鐘廣播，間隔1天
//                setAlarm(AlarmManager.RTC_WAKEUP,hour,minute,86400000,data[2],trashIntent)
//
//            }, hourNow, minuteNow, false).show
            MyTimePickDialog(this).show(supportFragmentManager,"_")
        }
//        初始化RecyclerView
//        upDateRecycler(getSQLTimer())


//        setRecyclerView()
        emptyImg()
        updateText()
    }

    private fun emptyImg(){
        if (getSQLTimer().size==0){
            binding.imageView2.visibility= View.VISIBLE
            binding.textView8.visibility = View.VISIBLE
        }else{
            binding.imageView2.visibility= View.GONE
            binding.textView8.visibility = View.GONE
        }
    }

//    設定RecyclerView內容
//    private fun setRecyclerView(){
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.RecyclerView.apply {
//
////              設定分隔線
//            addItemDecoration(
//                DividerItemDecoration(
//                    this@PushNotification,
//                    DividerItemDecoration.VERTICAL
//                )
//            )
//
//            setHasFixedSize(true)
//            setLayoutManager(layoutManager)
//            adapter=viewAdapter
//        }
//    }
//    private fun upDateRecycler(data: ArrayList<Array<Int>>){
//        //設定傳入recyclerview參數
//        viewAdapter.dataSet=data
//
//        //    這裡沒有東西
//        emptyImg()
//
//    }

//    取得SQL資料
    private fun getSQLTimer(): ArrayList<Array<Int>> {
        //get sqlite table time
        val db=Sqlite(this)
//    回傳(hour,minute,createAt)
        return db.getTimer()
    }

//    添加SQL資料
    private fun addSQLTimer(time:Array<Int>){
        //set sqlite table time
        val db=Sqlite(this)
//    傳入(hour,minute,createAt)
        db.addTimer(time)
    }

    private fun updateText(){
        val sql=getSQLTimer()
        if (sql.isNotEmpty()){
            binding.textView60.isVisible=true
            var result="抵達前"
            sql.onEach {
                when(it[2]){
                    1855->result+="5分鐘，"
                    1850->result+="10分鐘，"
                    1845->result+="15分鐘，"
                    1830->result+="30分鐘，"
                    1800->result+="1小時，"
                }
            }
            result+="\n將收到通知。"
            binding.textView60.text=result
        }else{
            binding.textView60.isVisible=false
        }
    }
////    覆寫RecyclerView的點擊監聽
//    override fun onItemClick(position: Int) {
//        val timerList=getSQLTimer()[position]
//        println(position)
//        val createAt=timerList[2]
//        fun updateTimer(){
//            TimePickerDialog(this,{
//                    _,hour,minute->
//                Sqlite(this).updateTimer(createAt,hour,minute)
//                upDateRecycler(getSQLTimer())
//            },timerList[hour],timerList[minute],false).show()
//
//        }
//
//        val alter=AlertDialog.Builder(this)
//        alter.setTitle("提示")
//        alter.setMessage("選擇修改或是刪除提醒")
//        alter.setPositiveButton("修改"
//        ) { _, _ -> updateTimer() }
//        alter.setNegativeButton("刪除"
//        ) { _, _ -> Sqlite(this).delTimer(createAt);upDateRecycler(getSQLTimer());cancelAlarm(createAt,trashIntent) }
//        alter.setNeutralButton("取消",null)
//        alter.show()
//
//    }

//    overload SetAlarm
    private fun setAlarm(type: Int, hour: Int, minute: Int, intervalMillis: Long, requestCode: Int, intent: Intent) {
        val now = Calendar.getInstance()
        val targetTime = now.clone() as Calendar
        targetTime.set(Calendar.HOUR_OF_DAY, hour)
        targetTime.set(Calendar.MINUTE, minute)
        targetTime.set(Calendar.SECOND, 0)
        targetTime.set(Calendar.MILLISECOND, 0)
        if (targetTime.before(now))
            targetTime.add(Calendar.DATE, 1)
        setAlarm(type, targetTime.timeInMillis, intervalMillis, requestCode, intent)

    }

//    設定鬧鐘
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(type: Int, triggerAtMillis: Long, intervalMillis: Long, requestCode: Int, intent: Intent) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(type, triggerAtMillis, intervalMillis, PendingIntent.getBroadcast(this,
            requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT))
        Log.d("alarm","alarm set")
    }

//    取消鬧鐘
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun cancelAlarm(requestCode: Int, intent: Intent) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(
            PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT))
        Log.d("alarm","alarm cancel")
    }



//    廣播接收
    class TrashReceiver:BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val c = Calendar.getInstance()
            val hourNow = c.get(Calendar.HOUR_OF_DAY)
            val minuteNow = c.get(Calendar.MINUTE)

//                發送提醒
            TkuNotification(p0!!, "垃圾車提醒", "垃圾車提醒").build("垃圾車提醒", "${hourNow}:${minuteNow},該倒垃圾啦(⁎⁍̴̛ᴗ⁍̴̛⁎)").show(R.string.trashReminder)
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
    fun setTime(selected:List<Boolean>){
        for((index,element) in selected.withIndex()){
            val code=requestCode[index]
            if (element){
                val t=timeList[index].split(":")
                val hour=t[0].toInt()
                val minute=t[1].toInt()
                addSQLTimer(arrayOf(hour,minute,code))
                setAlarm(AlarmManager.RTC_WAKEUP,hour,minute,86400000,code,trashIntent)
            }else{
                cancelAlarm(code,trashIntent)
                Sqlite(this).delTimer(code)
            }
        }
    }

//    時間選擇dialog
    class MyTimePickDialog(val context: PushNotification):DialogFragment(){
        lateinit var binding: GarbageTruckTimeBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding= GarbageTruckTimeBinding.inflate(layoutInflater)
            initView()
            return binding.root
        }
        private fun initView(){
            initCheckBox()
            binding.button2.setOnClickListener {
                context.setTime(arrayListOf<Boolean>().also { arrayList ->
                    getCheckBox().onEach {
                        arrayList.add(it.isChecked)
                    }
                }.toList())
                context.updateText()
                context.emptyImg()
                dismiss()
            }

        }
        private fun getCheckBox():List<CheckBox>{
            return listOf(
                binding.checkBox1,
                binding.checkBox2,
                binding.checkBox3,
                binding.checkBox4,
                binding.checkBox5
            )
        }
        private fun initCheckBox(){
            context.getSQLTimer().onEach {
                when(it[2]){
                    1855->binding.checkBox1.isChecked=true
                    1850->binding.checkBox2.isChecked=true
                    1845->binding.checkBox3.isChecked=true
                    1830->binding.checkBox4.isChecked=true
                    1800->binding.checkBox5.isChecked=true
                }
            }
        }

    }
}