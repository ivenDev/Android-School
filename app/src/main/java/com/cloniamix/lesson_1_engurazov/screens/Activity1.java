package com.cloniamix.lesson_1_engurazov.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloniamix.lesson_1_engurazov.R;

import java.util.TreeSet;

public class Activity1 extends AppCompatActivity {

    private EditText mNameET;
    private TreeSet<String> mNameList;
    private TextView mNameListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        mNameList = new TreeSet<>();

        mNameET = findViewById(R.id.et_name);
        mNameListTextView = findViewById(R.id.tv_name_list);

        Button saveBtn = findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(v -> saveName());

        Button showBtn = findViewById(R.id.btn_show);
        showBtn.setOnClickListener(v -> showNameList());


    }

    //проверяет введено ли имя, если да, то сохраняет его в список, иначе выводит тост
    private void saveName(){
        if (mNameET.getText().length() != 0) {
            String name = mNameET.getText().toString();
            mNameList.add(name);
            mNameET.getText().clear();
            showToast("Данные сохранены");
        } else {
            showToast("Введите имя");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    //собирает все имена в один String и выводит на экран
    private void showNameList(){
        String list = "Список учеников: \n";
        for (String a: mNameList){

            // FIXME: 04.06.2019 узнать про конкатенацию
            list = list + a + "\n";
        }

        mNameListTextView.setText(list);

        //mNameListTextView.setText(mNameList.toString());// выводит в таком формате: [имя1, имя2 и т.д]
    }


}
