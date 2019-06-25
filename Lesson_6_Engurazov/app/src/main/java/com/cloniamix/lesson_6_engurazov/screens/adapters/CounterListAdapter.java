package com.cloniamix.lesson_6_engurazov.screens.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment2;
import com.cloniamix.lesson_6_engurazov.POJO.Counter;
import com.cloniamix.lesson_6_engurazov.R;
import com.cloniamix.lesson_6_engurazov.screens.adapters.holders.CounterMultiTypeViewHolder;
import com.cloniamix.lesson_6_engurazov.screens.adapters.holders.CounterSingleTypeViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.cloniamix.lesson_6_engurazov.utils.Utils.ITEM_MULTIPLE;
import static com.cloniamix.lesson_6_engurazov.utils.Utils.ITEM_SINGLE;

public class CounterListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Counter> counters;
    private Fragment2.OnFragment2InteractionListener listener;

    public CounterListAdapter(ArrayList<Counter> counters, Fragment2.OnFragment2InteractionListener listener) {
        this.counters = counters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_SINGLE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_counter_single, parent, false);

            viewHolder = new CounterSingleTypeViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_counter_multy_type, parent, false);
            viewHolder = new CounterMultiTypeViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_SINGLE) {
            ((CounterSingleTypeViewHolder) holder).bind(counters.get(position), listener);
        } else {
            ((CounterMultiTypeViewHolder) holder).bind(counters.get(position), listener);

        }
    }

    @Override
    public int getItemCount() {
        return counters.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (counters.get(position).isSingleType()) return ITEM_SINGLE;
        else return ITEM_MULTIPLE;
    }
}
