package com.tkuLife.dorm.student

import com.tkuLife.dorm.student.reminder.PushNotification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tkuLife.dorm.BarTool
import com.tkuLife.dorm.R
import com.tkuLife.dorm.student.laundry.Laundry
import com.tkuLife.dorm.databinding.ActivityStdMainpageBinding
import com.tkuLife.dorm.student.stdPackage.PackagePage
import com.google.firebase.auth.FirebaseAuth

class StdMainPage : AppCompatActivity() {
    private lateinit var binding: ActivityStdMainpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStdMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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