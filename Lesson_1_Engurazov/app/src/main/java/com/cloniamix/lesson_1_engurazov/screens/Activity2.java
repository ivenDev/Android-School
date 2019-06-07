package com.cloniamix.lesson_1_engurazov.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloniamix.lesson_1_engurazov.R;
import com.cloniamix.lesson_1_engurazov.Student;

import java.util.HashMap;

public class Activity2 extends AppCompatActivity {

    private EditText mETStudentData;
    private TextView mTVData;
    private HashMap<Integer, Student> mStudentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mStudentList = new HashMap<>();

        mETStudentData = findViewById(R.id.et_student_data);
        mETStudentData.setOnKeyListener((v, keyCode, event) ->  {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                saveStudentData();
                return true;
            }
            return false;
        });

        mTVData = findViewById(R.id.tv_data);

        Button showDataBtn = findViewById(R.id.btn_show_data);
        showDataBtn.setOnClickListener(v -> showStudentData());

    }

    // берет текст из поля ввода, разбивает всю фразу на слова по разделителю(пробел),если данные
    // введены верно, то собирает объект Student и помещает его в список, если нет, то выводит
    // тост о формате ввода
    private void saveStudentData(){
        String message = "Введите данные в виде: \nИмя Фамилия Класс Год_Рождения";
        if (mETStudentData.getText().length() != 0) {
            String studentData = mETStudentData.getText().toString();
            String[] data = studentData.split("\\s");// разбивает по словам, где разделитель - пробел
            if (data.length == 4){
                Student student = new Student(data[0], data[1], data[2], data[3]);
                mStudentList.put((int)System.currentTimeMillis(), student);
                mETStudentData.getText().clear();
                showToast("Данные сохранены");
            } else showToast(message);

        } else showToast(message);
    }

    private void showToast(String message){
        Toast.makeText(this, message
                ,Toast.LENGTH_SHORT).show();
    }

    // собирает все данные студентов из списка в один String и выводит на экран
    private void showStudentData(){

        StringBuilder sb = new StringBuilder();
        for (Student student: mStudentList.values()){
            sb.append(student.getAllData()).append("\n\n");
        }
        String mainData = sb.toString();
        mTVData.setText(mainData);
    }


}
