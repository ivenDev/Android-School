package com.cloniamix.lesson_1_engurazov.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cloniamix.lesson_1_engurazov.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startNewActivity(View view){
        switch (view.getId()){
            case R.id.btn_screen_1:
                startActivity(new Intent(this, Activity1.class));
                break;

            case R.id.btn_screen_2:
                startActivity(new Intent(this, Activity2.class));
                break;
        }
    }
}
