package com.example.tkulife_pro.admin.packageManagement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore



class PackageViewModel:ViewModel() {
    private lateinit var database: FirebaseFirestore
    private val repository: MutableLiveData<ArrayList<HashMap<*,*>>> by lazy {
        MutableLiveData<ArrayList<HashMap<*,*>>>()
    }

    fun getSortList(mode:Int): LiveData<ArrayList<HashMap<*,*>>> {
        when(mode){
//            按時間
            0->{
                sortByTime()
            }
//            已領
            1->{
                sortByTaken(true)
            }
//            未領
            2->{
                sortByTaken(false)
            }
        }
        return repository
    }

    private fun sortByTime() {
        database= FirebaseFirestore.getInstance()
        val packageList=ArrayList<HashMap<*,*>>()
        database.collection("student").get().addOnSuccessListener {
            it.documents.onEach {
                try {
                    val pk=it["package"] as ArrayList<HashMap<*,*>>
                    packageList.addAll(pk)
                }catch (e:Exception){
                    Log.d("model error",e.toString())
                }

            }
            packageList.sortBy { ele->ele["time"] as Double }
            repository.value=packageList
        }
    }

    private fun sortByTaken(taken:Boolean){
        database= FirebaseFirestore.getInstance()
        val packageList=ArrayList<HashMap<*,*>>()
        database.collection("student").get().addOnSuccessListener {
            it.documents.onEach {
                try {
                    val pk=it["package"] as ArrayList<HashMap<*,*>>
                    packageList.addAll(pk)
                }catch (e:Exception){
                    Log.d("model error",e.toString())
                }
            }
            val newList=ArrayList<HashMap<*,*>>()
            packageList.onEach { element->
                if (element["taken"] as Boolean == taken) {
                    newList.add(element)
                }
            }
            repository.value=newList
        }
    }

}