package com.cloniamix.lesson_5_engurazov.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cloniamix.lesson_5_engurazov.R;

public class Activity2 extends AppCompatActivity {

    public static Intent createStartIntent(Context context){
        return new Intent(context,Activity2.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        findViewById(R.id.buttonGoTo3).setOnClickListener(v -> startActivity(
                Activity3.createStartIntent(Activity2.this)
        ));
    }
}
