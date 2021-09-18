package com.tkuLife.dorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.tkuLife.dorm.admin.AdminMainPage
import com.tkuLife.dorm.databinding.ActivityUserSelectBinding
import com.tkuLife.dorm.student.StdMainPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initView(){
//        設定Bar
        BarTool(this).setBundle("宿舍管理",R.color.barBlue)

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