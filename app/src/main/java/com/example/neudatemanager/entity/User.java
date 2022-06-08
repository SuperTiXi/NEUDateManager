package com.example.neudatemanager.entity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.neudatemanager.sqlite.DBOpenHelper;

public class User {

    private String name;
    private String password;

    public User(String name,String password) {
        this.name = name;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //向数据库中写入User对象的属性
    public long write(String nullColumnHack, Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"user.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",this.getName());
        contentValues.put("password",this.getPassword());
        return db.insert("user",nullColumnHack,contentValues);
    }

    //返回name的行数
    public int countOfName(String name,String nullColumnHack, Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"user.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("user",new String[]{"name"},"name = ?",new String[]{name},null,null,null);
        int count = cursor.getCount();
        cursor.close();
        return count;

    }

    //登陆函数
    public  boolean login(String name,String password,String nullColumnHack, Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"user.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("user",new String[]{"name","password"},"name = ?",new String[]{name},null,null,null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String passwordFromDB = cursor.getString(cursor.getColumnIndex("password"));
            if(passwordFromDB.equals(password)){
                return true;
            }

        }
        return false;
    }

    //修改密码
    public int modifyPassword(String nullColumnHack, Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",this.password);
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"user.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int modifyCount = db.update("user",contentValues,"name = ?",new String[]{this.name});
        return modifyCount;
    }

    //清空数据库用
    public void emptyDB(String nullColumnHack, Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"user.db",null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("user",null,null);
    }
}
