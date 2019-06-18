package com.cloniamix.lesson_5_engurazov.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cloniamix.lesson_5_engurazov.R;
import com.google.android.material.snackbar.Snackbar;

public class Activity3 extends AppCompatActivity {

    private static final int ACTIVITY_3_REQUEST_CODE = 0;

    private Button buttonGoTo5;

    public static Intent createStartIntent(Context context){
        return new Intent(context, Activity3.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        // Задание 2
        findViewById(R.id.buttonGoTo1).setOnClickListener(v -> startActivity(
                Activity1.createStartIntent(Activity3.this)
        ));

        //Задание 3
        buttonGoTo5 = findViewById(R.id.buttonGoTo5);
        buttonGoTo5.setOnClickListener(v -> startActivityForResult(
                Activity5.createStartIntent(Activity3.this), ACTIVITY_3_REQUEST_CODE
        ) );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_3_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String text = data.getStringExtra(Activity5.TEXT);
                    showSnackbar(buttonGoTo5, text);
                }
            }
        }
    }

    private void showSnackbar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
