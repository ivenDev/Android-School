package com.cloniamix.lesson_3_engurazov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Task1Activity extends AppCompatActivity {

    private TextView textViewAccountId;

    private LinearLayout linearLayoutName;
    private LinearLayout linearLayoutSurname;
    private LinearLayout linearLayoutEmail;
    private LinearLayout linearLayoutLogin;
    private LinearLayout linearLayoutRegion;


    public static Intent createStartIntent(Context context){
        return new Intent(context,Task1Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        init();
        updateUi(getUser());
    }

    private User getUser(){
        return new User("Ivan", "Ivanov" , "examle@mail.ru", "My Login",
                "Saransk", "Инженер");
    }

    private void init(){
        textViewAccountId = findViewById(R.id.textViewAccountId);

        linearLayoutName = findViewById(R.id.linearLayoutName);
        linearLayoutSurname = findViewById(R.id.linearLayoutSurname);
        linearLayoutEmail = findViewById(R.id.linearLayoutEmail);
        linearLayoutLogin = findViewById(R.id.linearLayoutLogin);
        linearLayoutRegion = findViewById(R.id.linearLayoutRegion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.getMenu().findItem(R.id.action_edit).setOnMenuItemClickListener(item -> {

            showToast(getResources().getString(R.string.menu_edit_item_toast_text));
            return true;
        });
        toolbar.setNavigationOnClickListener(v ->
                showToast(getResources().getString(R.string.menu_navigation_toast_text)));


        TextView textViewExitButton = findViewById(R.id.textViewExitButton);
        textViewExitButton.setOnClickListener(v ->
                showToast(getResources().getString(R.string.exit_toast_text)));
    }

    private void updateUi(User user){
        String accountIdAndPosition = "Карта №" + user.getId() + "\n" + user.getPosition();
        textViewAccountId.setText(accountIdAndPosition);

        TextView hint;
        TextView content;

        hint = linearLayoutName.findViewById(R.id.textViewHint);
        content = linearLayoutName.findViewById(R.id.textViewContent);
        hint.setText(getResources().getString(R.string.name_hint_text));
        content.setText(user.getName());

        hint = linearLayoutSurname.findViewById(R.id.textViewHint);
        content = linearLayoutSurname.findViewById(R.id.textViewContent);
        hint.setText(getResources().getString(R.string.surname_hint_text));
        content.setText(user.getSurname());

        hint = linearLayoutEmail.findViewById(R.id.textViewHint);
        content = linearLayoutEmail.findViewById(R.id.textViewContent);
        hint.setText(getResources().getString(R.string.email_hint_text));
        content.setText(user.getEmail());

        hint = linearLayoutLogin.findViewById(R.id.textViewHint);
        content = linearLayoutLogin.findViewById(R.id.textViewContent);
        hint.setText(getResources().getString(R.string.login_hint_text));
        content.setText(user.getLogin());

        hint = linearLayoutRegion.findViewById(R.id.textViewHint);
        content = linearLayoutRegion.findViewById(R.id.textViewContent);
        hint.setText(getResources().getString(R.string.region_hint_text));
        content.setText(user.getRegion());
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
