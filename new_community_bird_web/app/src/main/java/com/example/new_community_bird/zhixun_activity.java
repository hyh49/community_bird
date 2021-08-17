package com.example.new_community_bird;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.FormBody;
import okhttp3.Response;

public class zhixun_activity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    //资讯三个界面
    ViewPager2 viewPager;
    private LinearLayout llapp,llcontact,lltool,llmy;
    private ImageView ivapp,ivcontact,ivtool,ivmy,ivCurrent,ivc,ivb,ivd;

    private AppBarConfiguration mAppBarConfiguration;


    private Button btn_tools;
    private Button btn_else;
    private Button btn_mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhixun_activity);


        btn_tools = (Button) findViewById(R.id.btn_2);
        btn_else = (Button) findViewById(R.id.btn_3);
        btn_mine = (Button) findViewById(R.id.btn_4);


        initView();
//        initData();
        addListener();


    }
    //文章进行更新


    private void initData() {
    }



    private void initView() {


        //资讯的三个界面
        llapp=findViewById(R.id.id_tab);
        llapp.setOnClickListener(this);
        llcontact=findViewById(R.id.id_contact);
        llcontact.setOnClickListener(this);
        lltool=findViewById(R.id.id_tool);
        lltool.setOnClickListener(this);
        ivapp=findViewById(R.id.tab_health);
        ivcontact=findViewById(R.id.tab_test);
        ivtool=findViewById(R.id.tab_tool);
        ivapp.setSelected(true);
        ivCurrent=ivapp;

        //菜单按钮
        btn_tools = (Button) findViewById(R.id.btn_2);
        btn_else = (Button) findViewById(R.id.btn_3);
        btn_mine = (Button) findViewById(R.id.btn_4);

    }

    public void addListener() {

        //资讯的三个界面
        viewPager=findViewById(R.id.id_viewpager);
        ArrayList<Fragment>fragments=new ArrayList<>();
        fragments.add(BlankFragment.newInstance("咨询"));
        fragments.add(BlankFragment2.newInstance("饮食"));
        fragments.add(BlankFragment3.newInstance("运动"));
        myfragmentPagerAdapter pagerAdapter=new myfragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        //工具菜单按钮跳转
        btn_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(zhixun_activity.this,ToolActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //其他菜单按钮跳转
        btn_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(zhixun_activity.this,elseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //我的菜单按钮跳转
        btn_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(zhixun_activity.this,mineActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //资讯的三个界面
    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position){
            case R.id.id_tab:
                viewPager.setCurrentItem(0);
            case 0:
                ivapp.setSelected(true);
                ivCurrent=ivapp;
                break;
            case R.id.id_contact:
                viewPager.setCurrentItem(1);
            case 1:
                ivcontact.setSelected(true);
                ivCurrent=ivcontact;
                break;
            case R.id.id_tool:
                viewPager.setCurrentItem(2);
            case 2:
                ivtool.setSelected(true);
                ivCurrent=ivtool;
                break;
        }
    }

    public void onClick(View view){
        changeTab(view.getId());

    }
    //连接后端接口处
    public void state_click(View view) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//把用户的id变量放到value里
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/list")//http://wifi的ipv4地址:8888/数据库表
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值
                    String responseData=response.body().string();
                    JSONArray jsonArray=new JSONArray(responseData);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("shuimian",""+jsonObject.getString("shuimian"));//打印输出结果
                        Log.d("tnb",""+jsonObject.getString("tnb"));
                        Log.d("xueya",""+jsonObject.getString("xueya"));
                        Log.d("maiya",""+jsonObject.getString("maiya"));
                        Log.d("xinlu",""+jsonObject.getString("xinlu"));
                        Log.d("xxg",""+jsonObject.getString("xxg"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"发生成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    //192.168.0.104
    public void health_click(View view) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/health")
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值
                    String responseData=response.body().string();
                    JSONArray jsonArray=new JSONArray(responseData);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("title",""+jsonObject.getString("title"));
                        Log.d("link",""+jsonObject.getString("link"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"发生成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void eat_click(View view) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/eat")
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值
                    String responseData=response.body().string();
                    JSONArray jsonArray=new JSONArray(responseData);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("title",""+jsonObject.getString("title"));
                        Log.d("link",""+jsonObject.getString("link"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"发生成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void sport_click(View view) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/sport")
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值
                    String responseData=response.body().string();
                    JSONArray jsonArray=new JSONArray(responseData);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("title",""+jsonObject.getString("title"));
                        Log.d("link",""+jsonObject.getString("link"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"创建成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void add_click(View view) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    params.add("sleep","n");//是否失眠 y or n
                    params.add("bood_glucose","5.4");//血糖浓度
                    params.add("contra_blood_pressure","130");//收缩血压
                    params.add("diasto_blood_pressure","70");//舒张血压
                    params.add("heat_rate","56");//心动频率
                    params.add("age","70");//年龄
                    params.add("high","165");//身高
                    params.add("weigh","70");//体重
                    params.add("gender","2");// 性别 女1 男2
                    params.add("Cholesterol","3");//胆固醇 1：正常； 2：高于正常； 3：远高于正常
                    params.add("smoking","1");//吸烟  是 1  否 0
                    params.add("alco","0");//是否喝酒  是 1  否 0
                    params.add("Physical_activity","0");//是否经常运动 是 1 否 0
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/add")
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void xiugai_click(View view) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    params.add("sleep","n");//是否失眠 y or n
                    params.add("bood_glucose","6.5");//血糖浓度
                    params.add("contra_blood_pressure","150");//收缩血压
                    params.add("diasto_blood_pressure","70");//舒张血压
                    params.add("heat_rate","40");//心动频率
                    params.add("age","60");//年龄
                    params.add("high","165");//身高
                    params.add("weigh","70");//体重
                    params.add("gender","2");// 性别 女1 男2
                    params.add("Cholesterol","3");//胆固醇 1：正常； 2：高于正常； 3：远高于正常
                    params.add("smoking","1");//吸烟  是 1  否 0
                    params.add("alco","0");//是否喝酒  是 1  否 0
                    params.add("Physical_activity","0");//是否经常运动 是 1 否 0
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/updata")
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"发生成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(zhixun_activity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}