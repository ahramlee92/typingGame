package com.firstProject.typinggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GroupWatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_wating);

        Button backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), com.firstProject.typinggame.GameWaitingActivity.class);
            startActivity(intent);
        });

//        Button makingRoomBtn = (Button) findViewById(R.id.button11);
//        makingRoomBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MakingRoomActivity.class);
//                startActivity(intent);
//            }
//        });

        Button waitingGameBtn = (Button) findViewById(R.id.button7);
        waitingGameBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.firstProject.typinggame.GameWaitingActivity.class);
                startActivity(intent);
            }
        });

        Button mypageBtn = (Button) findViewById(R.id.button8);
        mypageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.firstProject.typinggame.MyPageActivity.class);
                startActivity(intent);
            }
        });

        Button playAloneBtn = (Button) findViewById(R.id.button6);
        playAloneBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });
    }
}