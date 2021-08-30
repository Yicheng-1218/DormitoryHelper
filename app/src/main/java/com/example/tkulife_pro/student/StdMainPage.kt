package com.example.tkulife_pro.student

import com.example.tkulife_pro.student.reminder.PushNotification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.MainActivity
import com.example.tkulife_pro.R
import com.example.tkulife_pro.student.laundry.Laundry
import com.example.tkulife_pro.databinding.ActivityStdMainpageBinding
import com.example.tkulife_pro.student.stdPackage.PackagePage
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class StdMainPage : AppCompatActivity() {
    private lateinit var binding: ActivityStdMainpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStdMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }
    private fun initView(){
//        設定BAR
        BarTool(this).setBundle("住宿生", R.color.barBlue)
//        取得學號
        val uid=FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
        uid.isNullOrEmpty().let{ if (!it)binding.textView7.text=uid }

//        鎖屏提醒
        binding.imageButton5.setOnClickListener {
            Intent(this,PushNotification::class.java).apply {
                startActivity(this)
            }
        }
//        洗衣烘衣
        binding.imageButton6.setOnClickListener {
            Intent(this, Laundry::class.java).apply {
                startActivity(this)
            }
        }
//        包裹狀態
        binding.imageButton8.setOnClickListener{
            Intent(this, PackagePage::class.java).apply {
                startActivity(this)
            }
        }

//        清除帳號
//        binding.button6.setOnClickListener {
//            val confirm = AlertDialog.Builder(this)
//            confirm.setMessage("是否清除帳密")
//            confirm.setTitle("確認視窗")
//            confirm.setNegativeButton("是"){ _,_->
//                AuthUI.getInstance()
//                    .signOut(this)
//                    .addOnCompleteListener {
//                        Toast.makeText(this,"您已登出此帳號!", Toast.LENGTH_LONG).show()
//                        Intent(this, MainActivity::class.java).apply {
//                            startActivity(this)
//                        }
//                    }
//            }
//            confirm.setPositiveButton("否", null)
//            confirm.show()
//        }
    }
}