package com.example.tkulife_pro.admin.packageManagement


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tkulife_pro.Keyboard
import com.example.tkulife_pro.databinding.ActivityPushPackagenotificationBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class PushNotification : AppCompatActivity() {
    private lateinit var binding: ActivityPushPackagenotificationBinding
    private lateinit var database:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPushPackagenotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    private fun initView(){
//        初始化db
        database=Firebase.firestore

//        查詢按鈕
        binding.imageButton18.setOnClickListener {
//            清除聚焦
            currentFocus?.clearFocus()
//            隱藏鍵盤
            Keyboard.hide(this,it)
//            取得editText.text
            val roomID=binding.roomBedText.editableText.toString()

//            取得用戶資料
            getUserDetail(roomID)
        }
    }


    private fun getUserDetail(roomID:String){
        val data= hashMapOf<String,Any>()
        try {
//            定義stdRef欄位
            var stdRef:DocumentReference? = null

//            取得roomList集合內的roomID文件
            val roomRef=database.collection("roomList").document(roomID)

//            取得roomID監聽
            roomRef.get().addOnSuccessListener { doc ->

//                如果資料不為空
                if (doc!=null && doc.data!=null){
                    Log.d("user roomRef",doc.data.toString())

//                    初始化stdRef
                    stdRef=doc.data!!["stdRef"] as DocumentReference

//                    final map 加入roomID
                    data["room_bed"] = roomID
                }else{
//                    查無床號
                    Log.d("package","room_bed not found")
                    Toast.makeText(this,"查無床號",Toast.LENGTH_SHORT).show()
                }

            }.continueWith {
//                接續roomRef執行序

//                取得stdRef監聽
                stdRef?.get()?.addOnSuccessListener { doc->
//                    如果資料不為空
                    if (doc!=null && doc.data!=null){
//                        temp=學生資料
                        val temp=doc.data as HashMap<String, Any>
//                        final map合併temp
                        data.putAll(temp)

//                        uid=學號
                        val uid=doc.id

//                        final map 加入學號
                        data["uid"] = uid

//                        印出測試
                        Log.d("user stdRef",data.toString())
                        Log.d("user stdRef",uid)
                        Log.d("user final map",data.toString())
                        runOnUiThread {
                            setFragment(data)
                        }

                    }
                }
            }

        }catch (e:Exception){
            Log.d("package",e.toString())
            Toast.makeText(this,"資料輸入錯誤",Toast.LENGTH_SHORT).show()
        }
    }

//    設定Fragment
    private fun setFragment(userData:HashMap<*,*>){
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id,PackagePushForm(userData)).commit()
    }

}