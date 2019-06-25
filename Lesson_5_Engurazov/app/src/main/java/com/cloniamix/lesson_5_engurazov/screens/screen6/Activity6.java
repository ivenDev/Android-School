package com.cloniamix.lesson_5_engurazov.screens.screen6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cloniamix.lesson_5_engurazov.POJO.ProposedService;
import com.cloniamix.lesson_5_engurazov.R;
import com.cloniamix.lesson_5_engurazov.screens.screen6.adapter.MyPagerAdapter;
import com.cloniamix.lesson_5_engurazov.screens.screen6.adapter.ServicesAdapter;
import com.cloniamix.lesson_5_engurazov.screens.screen6.utils.ItemOffsetDecoration;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Activity6 extends AppCompatActivity {

    public static Intent createStartIntent(Context context){
        return new Intent(context, Activity6.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);

        RecyclerView recyclerViewServicesList = findViewById(R.id.recyclerViewServicesList);
        ServicesAdapter servicesAdapter = new ServicesAdapter(getProposedServices(), this::showSnackbar);
        recyclerViewServicesList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewServicesList.addItemDecoration(new ItemOffsetDecoration(20));
        recyclerViewServicesList.setAdapter(servicesAdapter);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getImageUrls()));

        findViewById(R.id.textViewOfferServices).setOnClickListener(v ->
                showToast(getString(R.string.offer_a_services_toast_text)));

        findViewById(R.id.textViewAllServices).setOnClickListener(v ->
                showToast(getString(R.string.all_services_toast_text)));


    }


    private ArrayList<ProposedService> getProposedServices(){
        ArrayList<ProposedService> services = new ArrayList<>();

        services.add(new ProposedService("Царь пышка", "Скидка 10% на выпечку по коду"
                , "Лермонтовский пр, 52, МО №1" , "https://i.imgur.com/m1TCPyC.jpg"));
        services.add(new ProposedService("Царь пышка", "Скидка 10% на выпечку по коду"
                , "Лермонтовский пр, 52, МО №1" , "http://i.imgur.com/3wQcZeY.jpg"));
        services.add(new ProposedService("Царь пышка", "Скидка 10% на выпечку по коду"
                , "Лермонтовский пр, 52, МО №1" , "https://ibb.co/CMmZ0Xx"));
        services.add(new ProposedService("Царь пышка", "Скидка 10% на выпечку по коду"
                , "Лермонтовский пр, 52, МО №1" , "https://ibb.co/CMmZ0Xx"));

        return services;
    }


    private ArrayList<String> getImageUrls(){
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://i.imgur.com/m1TCPyC.jpg");
        urls.add("http://i.imgur.com/3wQcZeY.jpg");
        urls.add("https://i.imgur.com/m1TCPyC.jpg");
        urls.add("http://i.imgur.com/3wQcZeY.jpg");

        return urls;
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar(View view, ProposedService proposedService){
        Snackbar.make(view, proposedService.getName(),Snackbar.LENGTH_SHORT).show();
    }
}
