package com.example.tkulife_pro

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService(){
    val database = Firebase.firestore
    override fun onCreate() {
        super.onCreate()
        val user = Firebase.auth.currentUser
        val token = FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("fcm", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                Log.d("fcm", token.toString())
                if(user!=null){
                    val email = user.email!!.split('@')
                    val userRef = database.collection("student").document("${email[0]}")
                    Log.d("user", "${email[0]}")
// Set the "isCapital" field of the city 'DC'
                        userRef
                            .update("token", token)
                            .addOnSuccessListener { Log.d("user", "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w("user", "Error updating document", e) }
                    }
            })

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("fcm", "refresh token:$token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

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
            TkuNotification(this,"包裹提醒","包裹提醒").build("包裹提醒","您目前有包裹需領取! 寄件人:${remoteMessage.data["寄件人"]}").show(2)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}