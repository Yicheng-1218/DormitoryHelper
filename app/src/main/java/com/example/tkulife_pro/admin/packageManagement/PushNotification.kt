package com.example.tkulife_pro.admin.packageManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityPushNotificationBinding
import com.example.tkulife_pro.databinding.ActivityPushPackagenotificationBinding

class PushNotification : AppCompatActivity() {
    private lateinit var binding: ActivityPushPackagenotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushPackagenotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.fragmentContainerView.isVisible=false
        binding.imageButton18.setOnClickListener{
//            var dialog=PackagePushForm()
//            dialog.show(supportFragmentManager,"notificationFrom")
//            binding.fragmentContainerView.isVisible=true
            supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id,PackagePushForm()).commit()
        }
    }
}