package com.example.tkulife_pro

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent


class TkuNotification(private val context: Context, id :String,name:String) {

    private var channel:NotificationChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
    private val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    lateinit var content:Notification
    private val builder = Notification.Builder(context, id)


//    初始化channel建立
    init {
        channel.lockscreenVisibility=Notification.VISIBILITY_PUBLIC
    }

//    建立推播內容方法
    fun build(title:String,text:String): TkuNotification {
        builder.apply {
            setSmallIcon(R.drawable.email__1_)
            setContentTitle(title)
            setContentText(text)
            setAutoCancel(true)
            setVisibility(Notification.VISIBILITY_PUBLIC)
        }
        return this
    }
    fun build(title:String,text:String,channelID:String): TkuNotification {
        builder.apply {
            setSmallIcon(R.drawable.email__1_)
            setContentTitle(title)
            setContentText(text)
            setAutoCancel(true)
            setChannelId(channelID)
            setVisibility(Notification.VISIBILITY_PUBLIC)
        }
        return this
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun build(title:String, text:String,channelID:String,intent: Intent):TkuNotification{
        build(title, text,  channelID)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        return this
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun build(title:String, text:String, intent: Intent):TkuNotification{
        build(title, text)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        return this
    }

//    顯示推播內容
    fun show(id:Int){
        content= builder.build()
        manager.createNotificationChannel(channel)
        manager.notify(id, content)
    }

    fun getNotification():Notification{
        return builder.build()
    }
}