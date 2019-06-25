package com.cloniamix.lesson_6_engurazov.screens.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloniamix.lesson_6_engurazov.POJO.Counter;
import com.cloniamix.lesson_6_engurazov.R;
import com.cloniamix.lesson_6_engurazov.screens.adapters.CounterListAdapter;
import com.cloniamix.lesson_6_engurazov.utils.ItemOffsetDecoration;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private OnFragment2InteractionListener listener;

    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbarFragment2 = view.findViewById(R.id.toolbarFragment2);
        toolbarFragment2.inflateMenu(R.menu.menu_toolbar_fragment_2);

        toolbarFragment2.getMenu().findItem(R.id.menu_item_lamp).setOnMenuItemClickListener(item -> {
            listener.onLampItemClicked();
            return true;
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCounterList);
        CounterListAdapter counterListAdapter = new CounterListAdapter(getCounters(), listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(8));
        recyclerView.setAdapter(counterListAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2InteractionListener) {
            listener = (OnFragment2InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment2InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragment2InteractionListener {
        void onSendCounterReadingsClick(View view, String counterReadings);
        void onSendCounterMorningNightPeakReadingsClick(View view, String[] readings);
        void onMoreItemClick(View view);
        void onInfoItemClick();
        void onLampItemClicked();
    }

    private ArrayList<Counter> getCounters(){
        ArrayList<Counter> counters = new ArrayList<>();

        counters.add(new Counter(
                getString(R.string.cold_water_text)
                ,getString(R.string.cold_water_counter_number_text)
                ,R.drawable.ic_water_cold
                ,true
                ,true
                ,getString(R.string.alarm_text)
        ));
        counters.add(new Counter(
                getString(R.string.hot_water_text)
                ,getString(R.string.hot_water_counter_number_text)
                ,R.drawable.ic_water_hot
                ,true
                ,true
                ,getString(R.string.alarm_text)
        ));


        counters.add(new Counter(
                getString(R.string.electro_name_text)
                ,getString(R.string.electro_counter_number_text)
                ,R.drawable.ic_electro_copy
                ,false
                ,false
                ,getString(R.string.electro_alarm_text)
        ));

        return counters;
    }
}
