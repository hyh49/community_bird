package com.example.new_community_bird;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DBOpenHelper mDBOpenHelper;
    private EditText userId_editText;
    private EditText password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
         userId_editText = findViewById(R.id.name);
         password_editText = findViewById(R.id.password);
    }
    //注册按钮事件，跳转到注册界面
    public void register_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, register.class);
        startActivity(intent);
    }

//    登录按钮事件，进行登录
    public void login_onClick(View view) {
        final String userId = userId_editText.getText().toString().trim();
        final String password = password_editText.getText().toString().trim();
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(password)) {
            ArrayList<User> data = mDBOpenHelper.getAllData();
            boolean match = false;
            String istiao = "";//是否跳过信息登记
            for (int i = 0; i < data.size(); i++) {
                User user = data.get(i);
                if (userId.equals(user.getId()) && password.equals(user.getPassword())) {
                    match = true;
                    istiao=user.getAge();
                    break;
                } else {
                    match = false;
                }
            }
            if (match) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                if (!istiao.equals("")){
                    Intent intent = new Intent(this, zhixun_activity.class);
                    startActivity(intent);
                    finish();//销毁此Activity
//                    Toast.makeText(this, "老用户可以直接进入主页面了", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(this, infoActivity.class);
                    startActivity(intent);
                    finish();//销毁此Activity
                }
            } else {
                Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
        }
    }


    }

