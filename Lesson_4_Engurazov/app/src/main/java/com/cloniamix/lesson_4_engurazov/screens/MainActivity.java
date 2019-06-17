package com.cloniamix.lesson_4_engurazov.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cloniamix.lesson_4_engurazov.pojo.InfoItem;
import com.cloniamix.lesson_4_engurazov.utils.ItemOffsetDecoration;
import com.cloniamix.lesson_4_engurazov.screens.adapter.MyAdapter;
import com.cloniamix.lesson_4_engurazov.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.getMenu().findItem(R.id.action_home).setOnMenuItemClickListener(item -> {
            showToast(getString(R.string.home_menu_toast_text));
            return true;
        });
        toolbar.getMenu().findItem(R.id.action_info).setOnMenuItemClickListener(item -> {
                showDialog();
                return true;
        });
        toolbar.setNavigationOnClickListener(v -> finish());


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter adapter = new MyAdapter(getInfoItems(), this::showSnackbar);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                // FIXME: 16.06.2019 растянуть "охрана" на две ячейки
                switch(adapter.getItemViewType(position)){

                    case 1:
                        return 2;
                    case 2:
                        return 1;

                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new ItemOffsetDecoration(8));

        recyclerView.setAdapter(adapter);
    }


    private ArrayList<InfoItem> getInfoItems(){
        ArrayList<InfoItem> list = new ArrayList<>();
        list.add(new InfoItem(getString(R.string.bill_name_text),getString(R.string.bill_hint_text)
                , R.drawable.ic_bill,true));
        list.add(new InfoItem(getString(R.string.counter_name_text),getString(R.string.counter_hint_text)
                , R.drawable.ic_counter,true));
        list.add(new InfoItem(getString(R.string.installment_name_text),getString(R.string.installment_hint_text)
                , R.drawable.ic_installment,false));
        list.add(new InfoItem(getString(R.string.insurance_name_text),getString(R.string.insurance_hint_text)
                , R.drawable.ic_insurance,false));
        list.add(new InfoItem(getString(R.string.tv_name_text),getString(R.string.tv_hint_text)
                , R.drawable.ic_tv,false));
        list.add(new InfoItem(getString(R.string.homephone_name_text),getString(R.string.homephone_hint_text)
                , R.drawable.ic_homephone,false));
        list.add(new InfoItem(getString(R.string.guard_name_text),getString(R.string.guard_hint_text)
                , R.drawable.ic_guard, false));

        list.add(new InfoItem(getString(R.string.uk_contacts_name_text), R.drawable.ic_uk_contacts));
        list.add(new InfoItem(getString(R.string.request_name_text), R.drawable.ic_request));
        list.add(new InfoItem(getString(R.string.about_name_text), R.drawable.ic_about));


        return list;
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title_text))
                .setMessage(getString(R.string.dialog_message_text))
                .setPositiveButton("Ok"
                        , (dialog, which) -> dialog.cancel())
                .show();

    }


    private void showSnackbar(View view, InfoItem infoItem){
        Snackbar.make(view, infoItem.getName(),Snackbar.LENGTH_SHORT).show();
    }



}
