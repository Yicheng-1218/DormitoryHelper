package com.example.tkulife_pro

import android.content.Context
import android.content.SharedPreferences

class SharedXML(private val context: Context) {
    fun getXML(name:String): SharedPreferences? {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE) ?: createXML(name)
    }
    private fun createXML(name: String): SharedPreferences? {
        val table=context.getSharedPreferences(name,Context.MODE_PRIVATE)
        val editor=table.edit()
        editor.commit()
        return table
    }
}