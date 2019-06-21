package com.cloniamix.lesson_5_engurazov.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.cloniamix.lesson_5_engurazov.R;
import com.cloniamix.lesson_5_engurazov.screens.screen6.Activity6;


public class Activity1 extends AppCompatActivity {

    public static Intent createStartIntent(Context context){
        return new Intent(context,Activity1.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        //Задание 1
        Button buttonGoTo4 = findViewById(R.id.buttonGoTo4);
        buttonGoTo4.setOnClickListener(v -> startActivity(
                Activity4.createStartIntent(Activity1.this, System.currentTimeMillis()))
        );

        //Задание 2
        findViewById(R.id.buttonGoTo2).setOnClickListener(v -> startActivity(
                Activity2.createStartIntent(Activity1.this)));


        findViewById(R.id.buttonGoTo6).setOnClickListener(v -> startActivity(
                Activity6.createStartIntent(Activity1.this)
        ));
    }
}
