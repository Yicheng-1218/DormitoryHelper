package com.example.tkulife_pro.admin.packageManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tkulife_pro.databinding.ActivityPushNotificationBinding
import com.example.tkulife_pro.databinding.ActivityPushPackagenotificationBinding

class PushNotification : AppCompatActivity() {
    private lateinit var binding: ActivityPushPackagenotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushPackagenotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageButton18.setOnClickListener{
            var dialog=PackagePushForm()
            dialog.show(supportFragmentManager,"notificationFrom")

        }
    }
}