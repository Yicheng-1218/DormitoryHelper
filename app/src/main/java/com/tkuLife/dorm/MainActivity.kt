package com.tkuLife.dorm

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.tkuLife.dorm.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView(){
//        設定BAR
        BarTool(this).setBundle("淡江i生活",R.color.barRed)



//        宿舍管理按鈕
        binding.imageButton.setOnClickListener {
            Intent(this,UserSelect::class.java).apply {
                startActivity(this)
            }
        }

//        開啟FCM服務
        Intent(this,FCMService::class.java).apply {
            startService(this)
        }

//        取得登入狀態
        getUser()
    }
    private fun setBarTitle(title:String,colorHex:String){
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(colorHex)))
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)
        supportActionBar?.customView?.findViewById<TextView>(R.id.barTitle)?.text= title
    }

//    取得登入狀態
    private fun getUser(){
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in已登
            Log.d("user",user.email.toString())
        } else {
            // No user is signed in沒登入
            logIn()
        }
    }

//    登入方法
    private fun logIn(){

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_TkuLife_pro)
            .build()
        signInLauncher.launch(signInIntent)
    }

//    檢查登入結果
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.d("user",user.toString())
            Intent(this,FCMService::class.java).apply {
                startService(this)
            }
        } else {
            logIn()
        }
    }

//    登入啟動器
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
}