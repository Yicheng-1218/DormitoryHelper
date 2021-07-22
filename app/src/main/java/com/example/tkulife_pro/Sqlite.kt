package com.example.tkulife_pro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Sqlite(context: Context):SQLiteOpenHelper(context,"SQLdb",null,4) {

    private var timer="reminder"

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

//    建立提醒資料表
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists $timer (hour number,minute number,createAt number primary key)"
        db!!.execSQL(sql)
    }

//    重建table
    fun dropTable(){
        val db: SQLiteDatabase=writableDatabase
        db.execSQL("drop table $timer")
        onCreate(db)
        Log.d("SQL","table drop")
    }

//    新增提醒
    fun addTimer(timer:Array<Int>) {
        val hour=0
        val minute=1
        val createAt=2
        val values = ContentValues()
        values.put("hour", timer[hour])
        values.put("minute",timer[minute])
        values.put("createAt",timer[createAt])
        writableDatabase.insert(this.timer, null, values)
    }

//    刪除提醒
    fun delTimer(pk:Int){
        val db:SQLiteDatabase=writableDatabase
        db.execSQL("Delete from $timer where createAt=$pk")
        db.close()
    }

//    更新提醒
    fun updateTimer(pk: Int,new_hour:Int,new_minute:Int){
        val db:SQLiteDatabase=writableDatabase
        db.execSQL("UPDATE $timer set hour=$new_hour,minute=$new_minute where createAt=$pk;")
        println("資料修改完成")
        db.close()
    }

//    取得提醒資料表
    fun getTimer(): ArrayList<Array<Int>> {
        val cursor = readableDatabase.query(timer, arrayOf("hour", "minute","createAT"), null, null, null, null, null)
        val timer = ArrayList<Array<Int>>()

        try {
            if(cursor.moveToFirst()){
                do {
                    val hour = cursor.getInt(cursor.getColumnIndex("hour"))
                    val minute = cursor.getInt(cursor.getColumnIndex("minute"))
                    val createAt=cursor.getInt(cursor.getColumnIndex("createAt"))
                    val item = arrayOf(hour, minute,createAt)
                    timer.add(item)
                } while(cursor.moveToNext())

            }
        } catch (e:Exception) {

        } finally {
            if(cursor != null && !cursor.isClosed){
                cursor.close()
            }
        }
        return timer
    }
}