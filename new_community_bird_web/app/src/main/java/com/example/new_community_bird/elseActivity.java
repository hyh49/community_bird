package com.example.new_community_bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class elseActivity extends AppCompatActivity {

    private Button btn_message;
    private Button btn_tools;
    private Button btn_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_else);


        btn_message = (Button) findViewById(R.id.else_menu_btn_1);
        btn_tools = (Button) findViewById(R.id.else_menu_btn_2);
        btn_mine = (Button) findViewById(R.id.else_menu_btn_4);


        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(elseActivity.this,zhixun_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(elseActivity.this,ToolActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(elseActivity.this,mineActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}