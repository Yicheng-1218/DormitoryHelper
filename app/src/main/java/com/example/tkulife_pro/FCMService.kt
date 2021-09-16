package com.example.tkulife_pro

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import com.example.tkulife_pro.student.stdPackage.PackagePage
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
    private val database = Firebase.firestore
    private lateinit var timerXML : SharedPreferences

//    service onCreate
    override fun onCreate() {
        super.onCreate()
        timerXML = SharedXML(this).getXML("timer")!!
        val user = Firebase.auth.currentUser

//    取得token
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("fcm", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result

                Log.d("fcm", token.toString())
                if (user != null) {
                    val email = user.email!!.split('@')
                    val userRef = database.collection("student").document(email[0])
                    Log.d("user", email[0])
                    userRef
                        .update("token", token)
                        .addOnSuccessListener {
                            Log.d(
                                "user",
                                "DocumentSnapshot successfully updated!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w("user", "Error updating document", e) }
                }
            }

}

//    當有新Token產生時觸發
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("fcm", "refresh token:$token")
    }


//    前景接收FCM作業
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("fcm", "From: ${remoteMessage.from}")
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("fcm", "Message data payload: ${remoteMessage.data}")
        }
        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("fcm", "Message Notification Body: ${it.body}")
            TkuNotification(this,"包裹提醒","包裹提醒").apply {
                val intent=Intent(this@FCMService,PackagePage::class.java)
                build("包裹提醒","您目前有包裹需領取! 包裹編號後3碼:${remoteMessage.data["pid"]}",intent)
                show(R.string.packageReminder)
            }
        }

    }

}