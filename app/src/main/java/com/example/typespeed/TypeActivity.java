package com.example.typespeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class TypeActivity extends AppCompatActivity {
    private TypeActivityViewModel viewModel;
    private List<String> textList = new ArrayList<>();
    private TextView mainText;
    private TextView textViewTypeSpeed;
    private TextView textViewTimer;
    private Handler handler;
    private EditText editTextInput;
    private float passedSeconds = 0f;
    private boolean isFirstCharTyped = false;
    private int currentWord = 0;
    private int countChar = 0;
    private float speed = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        viewModel = new ViewModelProvider(this).get(TypeActivityViewModel.class);
        initViews();
        observeViewModel();
        viewModel.loadText();
        editTextWork();
        mainText.setText(String.join("", textList));

    }

    private void initViews() {
        mainText = findViewById(R.id.mainText);
        textViewTypeSpeed = findViewById(R.id.textViewSpeed);
        textViewTimer = findViewById(R.id.textViewTimer);
        editTextInput = findViewById(R.id.editTextInput);
        handler = new Handler();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, TypeActivity.class);
    }

    private void observeViewModel() {
        viewModel.getText().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                textList = strings;
                mainText.setText(String.join("", textList));
            }
        });
    }

    private void editTextWork() {
        editTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editFlag();
                Log.d("onTextChanged", textList.toString());
                underlineWord(textList.get(currentWord));
                if (s.toString().toLowerCase().equals(textList.get(currentWord))) {
                    if (currentWord < textList.size() - 1) {
                        currentWord++;
                        countChar += s.toString().length();
                        underlineWord(textList.get(currentWord));
                        editTextInput.setText("");
                    } else {
                        handler.removeCallbacksAndMessages(null);
                        Intent intent = EndActivity.newIntent(TypeActivity.this,
                                passedSeconds,
                                speed
                                );
                        startActivity(intent);
                        finish();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void editFlag() {
        if (!isFirstCharTyped) {
            isFirstCharTyped = true;
            editTextInput.setHint("");
            startTimer();
        }
    }

    private void underlineWord(String word) {
        String fullText = String.join("", textList);
        int startIndex = fullText.indexOf(word);
        int endIndex = startIndex + word.length();
        SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
        mainText.setText(spannableString);
    }

    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTimer();
                startTimer();
            }
        }, 100);

    }

    private void updateTimer() {
        passedSeconds += 0.1;
        textViewTimer.setText(String.format("%.1f", passedSeconds));
        if (passedSeconds != 0) {
            speed = (float) countChar / (passedSeconds / 60);
            textViewTypeSpeed.setText(String.format("%.2f", speed));
            Log.d("updateTimer", String.valueOf(speed));

        }
    }


}

