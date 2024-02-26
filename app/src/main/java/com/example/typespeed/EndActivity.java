package com.example.typespeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private TextView timePerson;
    private TextView textViewSpeedEnd;
    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        initViews();
        float time = getIntent().getFloatExtra("time", 0);
        float speed = getIntent().getFloatExtra("speed", 0);
        setValues(time, speed);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = StartActivity.newIntent(EndActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initViews(){
        timePerson = findViewById(R.id.timePerson);
        textViewSpeedEnd = findViewById(R.id.textViewSpeedEnd);
        buttonReturn = findViewById(R.id.buttonReturn);
    }

    private void setValues(float time, float speed){
        timePerson.setText(String.format("%.1f", time) + " c");
        textViewSpeedEnd.setText(String.format("%.2f", speed));
    }

    public static Intent newIntent(Context context, float time, float speed){
        Intent intent = new Intent(context, EndActivity.class);
        intent.putExtra("time", time);
        intent.putExtra("speed", speed);
        return intent;
    }
}