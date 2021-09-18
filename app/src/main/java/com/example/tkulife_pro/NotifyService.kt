package com.example.tkulife_pro

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception



class NotifyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    lateinit var listener:ValueEventListener

    private val database=Firebase.database.reference

    private fun stopListener(){
        database.removeEventListener(listener)
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate() {

        val workingNotify=TkuNotification(this,"洗衣提醒","洗衣提醒").also {
            it.build("洗衣提醒","追蹤狀態中...點擊取消","washReminder")
        }.getNotification().apply {
            contentIntent=PendingIntent.getBroadcast(this@NotifyService,0,
                Intent(this@NotifyService,StopServiceReceiver::class.java),PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        startForeground(1999,workingNotify)
    }

    override fun onDestroy() {
        stopListener()
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val num=intent?.getStringExtra("machineID")?.split('-')

        try {
            listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("notifyService","onDataChange")
                    val root = dataSnapshot.value as HashMap<*,*>
                    val type=root[num!![0]] as HashMap<*,*>
                    val floor=type[num[1]] as ArrayList<HashMap<*,*>>
                    val machine=floor[num[2].toInt()-1]
                    if (machine["con"]=="usable"){
                        TkuNotification(this@NotifyService,"洗衣提醒","洗衣提醒").build("洗衣提醒","快去拿衣服啦(⁎⁍̴̛ᴗ⁍̴̛⁎)").show(R.string.washReminder)
                        stopListener()
                        stopSelf()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("onCancelled", databaseError.toException())
                }
            }
            database.addValueEventListener(listener)
        }catch (e:Exception){
            Log.d("ServiceCatch",e.toString())
        }

        return START_NOT_STICKY
    }


    class StopServiceReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p0?.stopService(Intent(p0,NotifyService::class.java))
        }

    }
}