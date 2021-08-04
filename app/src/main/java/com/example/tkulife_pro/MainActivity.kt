package com.example.tkulife_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tkulife_pro.databinding.ActivityMainBinding
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
            .build()
        signInLauncher.launch(signInIntent)
    }

//    檢查登入結果
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.d("user",user.toString())
            Intent(this,FCMService::class.java).apply {
                startService(this)
            }
        } else {

        }
    }

//    登入啟動器
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
}