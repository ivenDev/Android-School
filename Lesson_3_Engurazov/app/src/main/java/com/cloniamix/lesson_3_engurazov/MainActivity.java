package com.cloniamix.lesson_3_engurazov;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectTask(View view){
        switch (view.getId()){
            case R.id.buttonActivity1:
                startActivity(Task1Activity.createStartIntent(this));
                break;

            case R.id.buttonActivity2:
                startActivity(Task2Activity.createStartIntent(this));
                break;

            case R.id.buttonActivity3:
                startActivity(Task3Activity.createStartIntent(this));
                break;
        }
    }
}
