package com.example.tkulife_pro

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE


class tkuNotification(private val context: Context,private val id :String, private val name:String) {
    private var channel:NotificationChannel
    private val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    lateinit var content:Notification

    //    建立channel方法
    private fun setChannel(): NotificationChannel {
        return NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
    }

//    初始化channel建立
    init {
        channel=setChannel()
        channel.lockscreenVisibility=Notification.VISIBILITY_PUBLIC
    }

//    建立推播內容方法
    fun build(title:String,text:String): tkuNotification {
        val builder = Notification.Builder(context, id)
        builder.setSmallIcon(R.drawable.email__1_)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setVisibility(Notification.VISIBILITY_PUBLIC)

        content= builder.build()
        return this
    }

//    顯示推播內容
    fun show(id:Int){
        manager.createNotificationChannel(channel)
        manager.notify(id, content)
        println("發送notify")
    }

}