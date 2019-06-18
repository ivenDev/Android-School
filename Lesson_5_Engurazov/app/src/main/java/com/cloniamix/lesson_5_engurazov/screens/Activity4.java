package com.cloniamix.lesson_5_engurazov.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;

import com.cloniamix.lesson_5_engurazov.R;


public class Activity4 extends AppCompatActivity {

    public static final String EXTRA_TIME = "com.cloniamix.lesson_5_engurazov Time";

    private long timeInMillis;
    private TextView textViewTime;

    public static Intent createStartIntent(Context context, long timeInMillis){
        Intent intent = new Intent(context,Activity4.class);
        intent.putExtra(EXTRA_TIME, timeInMillis);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        textViewTime = findViewById(R.id.textViewTime);

        Button buttonGoTo4Again = findViewById(R.id.buttonGoTo4Again);
        buttonGoTo4Again.setOnClickListener(v ->
            startActivity(createStartIntent(Activity4.this, System.currentTimeMillis())
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));

        timeInMillis = getIntent().getLongExtra(EXTRA_TIME,0);
        updateUI();
    }

    private void updateUI() {

        String dateString = DateFormat.format("dd.MM.yy\nHH.mm.ss", timeInMillis).toString();
        textViewTime.setText(dateString);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        long newTimeInMillis = intent.getLongExtra(EXTRA_TIME,0);
        if (newTimeInMillis != timeInMillis) {
            setIntent(intent);
            timeInMillis = newTimeInMillis;
            updateUI();
        }

    }
}
