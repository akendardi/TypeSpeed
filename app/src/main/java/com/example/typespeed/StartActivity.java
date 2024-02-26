package com.example.typespeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initViews();
        listeners();
    }

    private void initViews(){
        button = findViewById(R.id.button);
    }

    private void listeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TypeActivity.newIntent(StartActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context, StartActivity.class);
    }

}