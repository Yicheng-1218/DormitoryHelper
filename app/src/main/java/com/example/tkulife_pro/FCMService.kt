package com.example.tkulife_pro

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService(){


    override fun onCreate() {
        super.onCreate()
        val token = FirebaseMessaging.getInstance().token
        Log.d("fcm", "refresh token:$token")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("fcm", "refresh token:$token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("fcm", "From: ${remoteMessage.from}")
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("fcm", "Message data payload: ${remoteMessage.data}")
        }
        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("fcm", "Message Notification Body: ${it.body}")
            TkuNotification(this,"包裹提醒","包裹提醒").build("包裹提醒","您目前有包裹需領取!${remoteMessage.data}").show(2)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}