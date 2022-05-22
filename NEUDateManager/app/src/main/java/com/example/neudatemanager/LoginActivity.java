package com.example.neudatemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neudatemanager.entity.User;
import com.example.neudatemanager.sqlite.DBOpenHelper;

public class LoginActivity extends AppCompatActivity {
    TextView name;
    TextView password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //变量赋值
        name = findViewById(R.id.nameInput);
        password = findViewById(R.id.passwordInput);
        login = findViewById(R.id.loginButton);

        //传参用
        SharedPreferences sharedPreferences = getSharedPreferences(null,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        //登录按钮的点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameGet = name.getText().toString();
                String passwordGet = password.getText().toString();//获取输入的值

                //创建User对象，准备页面的跳转
                User user = new User(nameGet,passwordGet);
                Log.i("name",user.getName());
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                int count = user.countOfName(nameGet,null,LoginActivity.this);
                //检测数据库中拥有该名称的数量
                //为0则注册
                if(count==0){
                    if(user.write(null,LoginActivity.this)==-1){
                        Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                        editor.putString("creator",nameGet);
                        editor.commit();
                        startActivity(intent);
                    }
                }
                //为1则登录
                else if(count==1){
                        if(user.login(nameGet,passwordGet,null,LoginActivity.this)){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                            editor.putString("creator",nameGet);
                            editor.commit();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                        }
                }

                  // 清空数据用
//                user.emptyDB(null,LoginActivity.this);

            }
        });
    }
}