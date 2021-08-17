package com.example.new_community_bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToolActivity extends AppCompatActivity {

    private Button btn_message;
    private Button btn_else;
    private Button btn_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);


        btn_message = (Button) findViewById(R.id.tool_menu_btn_1);
        btn_else = (Button) findViewById(R.id.tool_menu_btn_3);
        btn_mine = (Button) findViewById(R.id.tool_menu_btn_4);


        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToolActivity.this,zhixun_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToolActivity.this,elseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToolActivity.this,mineActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}