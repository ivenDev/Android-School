package com.cloniamix.lesson_6_engurazov.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment2;
import com.cloniamix.lesson_6_engurazov.POJO.Counter;
import com.cloniamix.lesson_6_engurazov.R;
import com.cloniamix.lesson_6_engurazov.screens.adapters.holders.CounterViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CounterListAdapter extends RecyclerView.Adapter<CounterViewHolder> {

    private ArrayList<Counter> counters;
    private Fragment2.OnFragment2InteractionListener listener;

    public CounterListAdapter(ArrayList<Counter> counters, Fragment2.OnFragment2InteractionListener listener) {
        this.counters = counters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CounterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_counter_details, parent, false);

        return new CounterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounterViewHolder holder, int position) {
        holder.bind(counters.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return counters.size();
    }
}
