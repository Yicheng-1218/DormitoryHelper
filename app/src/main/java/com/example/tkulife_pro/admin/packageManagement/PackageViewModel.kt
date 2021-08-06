package com.example.tkulife_pro.admin.packageManagement

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback


@SuppressLint("StaticFieldLeak")
class PackageViewModel:ViewModel() {
    private lateinit var database: FirebaseFirestore
    lateinit var context: Context
    private val repository: MutableLiveData<ArrayList<HashMap<*,*>>> by lazy {
        MutableLiveData<ArrayList<HashMap<*,*>>>()
    }

    private val getPackageList by lazy{
        database= FirebaseFirestore.getInstance()
        ArrayList<HashMap<*,*>>().also { list->
            database.collection("roomList").get().addOnSuccessListener {
                it.documents.onEach { roomID ->
                    val stdRef = roomID["stdRef"] as DocumentReference
                    stdRef.get().addOnSuccessListener { user ->
                        try {
                            val pk = ArrayList<HashMap<*, *>>().also { pk ->
                                Log.d("model pack", user["package"].toString())
                                pk.addAll(user["package"] as ArrayList<HashMap<*, *>>)
                            }.onEach { map ->
                                map as HashMap<String, String>
                                map["roomID"] = roomID.id
                            }
                            list.addAll(pk)
                        } catch (e: Exception) {
                            Log.d("model error", e.toString())
                        }

                        sortByTime(list)
                    }
                }
            }
        }


    }

    fun getSortList(): LiveData<ArrayList<HashMap<*,*>>> {
        getPackageList
        return repository
    }

    fun searchByID(roomID:String):LiveData<ArrayList<HashMap<*,*>>>{
        return MutableLiveData<ArrayList<HashMap<*,*>>>().also { live->
            database= FirebaseFirestore.getInstance()

                database.collection("roomList").document(roomID).get().addOnSuccessListener {
                    try {
                        val stdRef=it["stdRef"] as DocumentReference
                        stdRef.get().addOnSuccessListener { user->
                            val packageList= ArrayList<HashMap<*,*>>().also { list->
                                list.addAll(user["package"] as ArrayList<HashMap<*, *>>)
                            }.onEach { map->
                                map as HashMap<String,String>
                                map["roomID"]=roomID
                            }
                            live.value=packageList
                        }
                    }catch (e:Exception){
                        Toast.makeText(context,"查無學號",Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }


    private fun sortByTime(packageList:ArrayList<HashMap<*,*>>) {
        Log.d("model time",packageList.toString())
        packageList.sortBy { ele->ele["time"] as Double }
        repository.value=packageList
    }

}