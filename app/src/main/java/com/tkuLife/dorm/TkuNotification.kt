package com.tkuLife.dorm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent


class TkuNotification(val context: Context, channelID :String, name:String){


    private var channel= NotificationChannel(channelID,name,NotificationManager.IMPORTANCE_HIGH)
    private val manager= context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private val builder = Notification.Builder(context, channelID)
    private lateinit var content:Notification

    constructor(context: Context, channelID: String, name: String,level:Int) : this(context, channelID, name) {
        channel= NotificationChannel(channelID,name,level)
    }

//    初始化channel建立
    init {
        channel.lockscreenVisibility=Notification.VISIBILITY_PUBLIC
    }


//    建立推播內容方法
    fun build(title:String,text:String?): TkuNotification {
        builder.apply {
            setSmallIcon(R.drawable.email__1_)
            setContentTitle(title)
            setContentText(text)
            setAutoCancel(true)
            setVisibility(Notification.VISIBILITY_PUBLIC)
        }
        content= builder.build()
        manager.createNotificationChannel(channel)
        return this
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    fun build(title:String,text:String?, intent: Intent): TkuNotification {
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        build(title, text)
        return this
    }

//    顯示推播內容
    fun show(id:Int){
        manager.notify(id, content)
    }

    fun getNotification():Notification{
        return content
    }
}