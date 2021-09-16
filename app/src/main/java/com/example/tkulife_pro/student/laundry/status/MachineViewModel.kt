package com.example.tkulife_pro.student.laundry.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MachineViewModel : ViewModel() {
    private lateinit var database: DatabaseReference
    private lateinit var listener: ValueEventListener
//    可變串流儲藏庫
    private val repository:MutableLiveData<HashMap<*,*>> by lazy {
        MutableLiveData<HashMap<*,*>>().also {
            valueListener()
        }
    }

    override fun onCleared() {
        database.removeEventListener(listener)
    }

    fun getRealtimeData():LiveData<HashMap<*,*>>{
//        取得儲藏庫資料root = realtime/root資料
        return repository
    }

    private fun valueListener(){
//        建立資料庫實例
        database = Firebase.database.reference

        listener=object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

//                設定repository的value=realtime資料的root
                repository.value=snapshot.value as HashMap<*, *>
            }

            //            資料取得失敗
            override fun onCancelled(error: DatabaseError) {
                repository.value= hashMapOf("msg" to "Firebase出現錯誤")
            }

        }
//        新增監聽事件
        database.addValueEventListener(listener)
    }
}