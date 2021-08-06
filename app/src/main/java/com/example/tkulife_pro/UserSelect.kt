package com.example.tkulife_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.admin.AdminMainPage
import com.example.tkulife_pro.databinding.ActivityUserSelectBinding
import com.example.tkulife_pro.student.StdMainPage
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import okhttp3.internal.immutableListOf


class UserSelect : AppCompatActivity() {
    private lateinit var binding: ActivityUserSelectBinding
    private val auth=FirebaseAuth.getInstance().currentUser
    private val database=Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
//        返回鍵
        binding.button3.setOnClickListener {
            super.onBackPressed()
        }
//        學生主頁
        binding.imageButton3.setOnClickListener {
            Intent(this,StdMainPage::class.java).apply {
                startActivity(this)
            }
        }
//        管理員主頁
        binding.imageButton2.setOnClickListener {
            val uid=auth?.email?.split('@')?.get(0)
            if (uid != null) {
                isAdmin(uid)
            }
        }
//        清除帳密
        binding.button6.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("是否清除帳密")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("是"){ _,_->
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        Toast.makeText(this,"您已登出此帳號!",Toast.LENGTH_LONG).show()
                        Intent(this,MainActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
            }
            confirm.setPositiveButton("否", null)
            confirm.show()
        }
    }

//    管理員檢查
    private fun isAdmin(uid:String){
        database.collection("admin").get().addOnSuccessListener { doc->
            val adminList= arrayListOf<String>().also { list->
                doc.documents.onEach { list.add(it.id) }
            }
            Log.d("adminList",adminList.toString())
            if (uid in adminList){
                Intent(this,AdminMainPage::class.java).apply {
                    startActivity(this)
                }
            }else{
                val noAccess= AlertDialog.Builder(this)
                noAccess.setMessage("您沒有管理員權限")
                noAccess.setTitle("錯誤")
                noAccess.setPositiveButton("我知道了",null)
                noAccess.show()
            }
        }


    }
}