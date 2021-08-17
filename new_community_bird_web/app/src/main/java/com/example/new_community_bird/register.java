package com.example.new_community_bird;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity implements View.OnClickListener{
    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private ImageView mIvRegisteractivityShowcode;
    private EditText userId_editText;
    private EditText userName_editText;
    private EditText password_editText;
    private EditText mEtRegisteractivityPhonecodes;
    private Button checkbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView() {
         userId_editText = findViewById(R.id.Id);
         userName_editText = findViewById(R.id.username);
         password_editText = findViewById(R.id.password);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mEtRegisteractivityPhonecodes=findViewById(R.id.et_registeractivity_phoneCodes);
        checkbutton=findViewById(R.id.agree);

        mIvRegisteractivityShowcode.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.button2:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                String userid = userId_editText.getText().toString().trim();
                String usename = userName_editText.getText().toString().trim();
                String password = password_editText.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                //注册验证
                if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode)
                        ) {
                    if (phoneCode.equals(realCode)) {
                        //将用户名和密码加入到数据库中
                        mDBOpenHelper.adduser(userid,usename,password,"","","","");
                        Intent intent2 = new Intent(this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
