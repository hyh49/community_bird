package com.example.new_community_bird;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class infoActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityId;
    private EditText mEtRegisteractivityage;
    private EditText mEtRegisteractivityhigh;
    private EditText mEtRegisteractivityweigh;
    private EditText mEtRegisteractivitygender;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;
    private RadioGroup rdg_gender;
    private  String gender_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);
        rdg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rbtn_male){
                        gender_1="2";
                }else{
                    gender_1="1";
                }
            }
        });
    }



    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityId = findViewById(R.id.et_registeractivity_id);
        mEtRegisteractivityage = findViewById(R.id.et_registeractivity_age);
        mEtRegisteractivityhigh = findViewById(R.id.et_registeractivity_high);
        mEtRegisteractivityweigh = findViewById(R.id.et_registeractivity_weigh);
//        mEtRegisteractivitygender = findViewById(R.id.et_registeractivity_gender);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);
        rdg_gender=(RadioGroup)findViewById(R.id.rdg_gender);


        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvRegisteractivityBack.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, zhixun_activity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                String Id = mEtRegisteractivityId.getText().toString().trim();
                String age = mEtRegisteractivityage.getText().toString().trim();
                String high = mEtRegisteractivityhigh.getText().toString().trim();
                String weigh = mEtRegisteractivityweigh.getText().toString().trim();
//                String gender = mEtRegisteractivitygender.getText().toString().trim();
                String gender=gender_1;
                //注册验证
                if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(high) && !TextUtils.isEmpty(weigh)
                        && !TextUtils.isEmpty(gender)&& !TextUtils.isEmpty(Id) ) {
                        //将用户信息加入到数据库中
                        mDBOpenHelper.updatainfo(age,high,weigh,gender,Id);
                        Intent intent2 = new Intent(this, zhixun_activity.class);
                        startActivity(intent2);

                }else {
                    Toast.makeText(this, "请先完善信息后进入", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}