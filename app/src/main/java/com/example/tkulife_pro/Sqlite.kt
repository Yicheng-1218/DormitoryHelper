package com.example.tkulife_pro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Sqlite(context: Context):SQLiteOpenHelper(context,"SQLdb",null,4) {

    private var timer="reminder"

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists $timer (hour number,minute number)"
        db!!.execSQL(sql)
    }

    fun addTimer(timer:Array<Int>) {
        val hour=0
        val minute=1
        val values = ContentValues()
        values.put("hour", timer[hour])
        values.put("minute",timer[minute])
        writableDatabase.insert(this.timer, null, values)
    }

    fun delTimer(hour:Int,minute: Int){
        val db:SQLiteDatabase=writableDatabase
        db.execSQL("Delete from $timer where hour=$hour and minute=$minute")
        db.close()
    }

    fun updateTimer(old_hour:Int,old_minute:Int,new_hour:Int,new_minute:Int){
        val db:SQLiteDatabase=writableDatabase
        db.execSQL("UPDATE $timer set hour=$new_hour,minute=$new_minute where hour=$old_hour and minute=$old_minute ;")
        println("資料修改完成")
        db.close()
    }

    fun getTimer(): ArrayList<Array<Int>> {
        val cursor = readableDatabase.query(timer, arrayOf("hour", "minute"), null, null, null, null, null)
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