package com.cloniamix.lesson_5_engurazov.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloniamix.lesson_5_engurazov.POJO.Data;
import com.cloniamix.lesson_5_engurazov.R;

public class Activity5 extends AppCompatActivity {

    public static final String TEXT = "myText";
    public static final String DATA_KEY = "myData";
    private EditText editText;
    private Data data;


    public static Intent createStartIntent(Context context){
        return new Intent(context,Activity5.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        editText = findViewById(R.id.editText);

        //задание3
        findViewById(R.id.buttonDeliverResult).setOnClickListener(v -> {
            if (getTextFromEditText() != null) {
                Intent intent = new Intent();
                intent.putExtra(TEXT, getTextFromEditText());
                setResult(RESULT_OK, intent);
                finish();
            } else showToast(getString(R.string.activity_5_toast_text));
        });

        //Задание 4
        TextView textViewDataText = findViewById(R.id.textViewDataText);

        if (savedInstanceState != null){
            data = savedInstanceState.getParcelable(DATA_KEY);
            if (data != null){
                textViewDataText.setText(data.getValue());
            }
        }

        findViewById(R.id.buttonSave).setOnClickListener(v -> {
            String text = getTextFromEditText();
            if (text != null) {
                data = new Data(text);
                textViewDataText.setText(text);
            } else showToast(getString(R.string.activity_5_toast_text));
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DATA_KEY, data);
    }


    private String getTextFromEditText(){

        if (editText.getText().length() != 0){
            return editText.getText().toString();
        }
        return null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
