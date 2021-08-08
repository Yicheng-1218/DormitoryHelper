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
    }

//    管理員檢查
    private fun isAdmin(uid:String){
        val noAccess=AlertDialog.Builder(this).apply {
            setMessage("您沒有管理員權限")
            setTitle("錯誤")
            setPositiveButton("確認",null)
        }

        if (uid.isNotEmpty()){
            database.collection("admin").get().addOnSuccessListener { doc->
                val adminList= arrayListOf<String>().also { list->
                    doc.documents.onEach { list.add(it.id) }
                }
                Log.d("adminList",adminList.toString())
                if (uid in adminList){
                    Intent(this,AdminMainPage::class.java).apply {
                        startActivity(this)
                    }
                }
                else{
                    noAccess.show()
                }
            }
        }else{
            noAccess.show()
        }



    }
}