package com.example.neudatemanager.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.neudatemanager.sqlite.DBOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
    private String name;
    private String startTime;
    private String endTime;
    private String creator;
    private String day;


    public Schedule(String creator) {
        this.creator = creator;
    }
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Schedule() {
    }

    public Schedule(String name,String startTime,String endTime,String creator,String day) {
        this.name = name;
        this.creator =creator;
        this.startTime = startTime;
        this.day = day;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator =creator;
    }


    //写入Schedule信息
    public long write(String nullColumnHack,Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"schedule.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",this.getName());
        contentValues.put("startTime",this.getStartTime());
        contentValues.put("endTime",this.getEndTime());
        contentValues.put("creator",this.getCreator());
        contentValues.put("day",this.getDay());
        return db.insert("schedule",nullColumnHack,contentValues);
    }

    //返回该日期下的数据库的指针
    public Cursor getCursorByDay(String nullColumnHack,Context context,String day){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"schedule.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("schedule",new String[]{"name as _id","startTime","endTime"},"day = ? and creator = ?",new String[]{day,this.creator},null,null,null);
        return cursor;
    }

    //格式化日期
    public String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate  = simpleDateFormat.format(date);
        return formatDate;
    }

    //格式化时间
    public String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String formatDate  = simpleDateFormat.format(date);
        return formatDate;
    }
    //获取指针
    public Cursor getCursor(String nullColumnHack,Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"schedule.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("schedule",new String[]{"name","startTime","endTime","creator","day"},"name = ?",new String[]{this.creator},null,null,null);

        return cursor;
    }

    //修改日程
    public long modify(String nullColumnHack,Context context,Schedule schedule){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"schedule.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",schedule.getName());
        contentValues.put("startTime",schedule.getStartTime());
        contentValues.put("endTime",schedule.getEndTime());
        contentValues.put("creator",schedule.getCreator());
        contentValues.put("day",schedule.getDay());

        return db.update("schedule",contentValues,"name = ? and startTime = ? and endTime = ? and creator = ? and day = ?",new String[]{this.name,this.startTime,this.endTime,this.creator,this.day});
    }

    //删除日程
    public long delete(String nullColumnHack,Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"schedule.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        return db.delete("schedule","name = ? and startTime = ? and endTime = ? and creator = ? and day = ?",new String[]{this.name,this.startTime,this.endTime,this.creator,this.day});
    }
    //清空数据库用
    public void emptyDB(String nullColumnHack, Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"schedule.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("schedule",null,null);
    }
}
