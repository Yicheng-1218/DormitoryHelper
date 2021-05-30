package com.example.tkulife_pro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.math.min

class Sqlite(context: Context):SQLiteOpenHelper(context,"SQLdb",null,4) {

    private var Timer="reminder"

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists $Timer ( id integer PRIMARY KEY autoincrement, hour number,minute number)"
        db!!.execSQL(sql)
    }

    fun addTimer(timer:Array<Int>) {
        val hour=0
        val minute=1
        val values = ContentValues()
        values.put("hour", timer[hour])
        values.put("minute",timer[minute])
        writableDatabase.insert(Timer, null, values)
    }
//    TODO 新增刪除方法
    fun getTimer(): ArrayList<Array<Int>> {
        val cursor = readableDatabase.query(Timer, arrayOf("hour", "minute"), null, null, null, null, null)
        val timer = ArrayList<Array<Int>>()

        try {
            if(cursor.moveToFirst()){
                do {
                    val hour = cursor.getInt(cursor.getColumnIndex("hour"))
                    val minute = cursor.getInt(cursor.getColumnIndex("minute"))
                    val item = arrayOf(hour, minute)
                    timer.add(item)
                } while(cursor.moveToNext())

            }
        } catch (e:Exception) {

        } finally {
            if(cursor != null && !cursor.isClosed){
                cursor.close()
            }
        }

        println("總共有 ${cursor.count} 筆資料")
        return timer

    }
}