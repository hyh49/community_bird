//package com.example.new_community_bird;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class qita extends AppCompatActivity {
//
//    private DrawerLayout mDrawerLayout;
//    private Toolbar mToolbar;
//    private Button btn_tools;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//
//
//        btn_tools = (Button) findViewById(R.id.btn_1);
//        btn_tools.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(qita.this,ToolActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}