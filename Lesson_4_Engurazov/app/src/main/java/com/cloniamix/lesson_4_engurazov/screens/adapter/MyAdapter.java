package com.cloniamix.lesson_4_engurazov.screens.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloniamix.lesson_4_engurazov.pojo.InfoItem;
import com.cloniamix.lesson_4_engurazov.utils.MyClickListener;
import com.cloniamix.lesson_4_engurazov.R;
import com.cloniamix.lesson_4_engurazov.screens.adapter.holders.MultipleUtilityViewHolder;
import com.cloniamix.lesson_4_engurazov.screens.adapter.holders.SingleUtilityViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<InfoItem> items;
    private MyClickListener listener;

    public MyAdapter(ArrayList<InfoItem> items, MyClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;


        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_single, parent, false);

            viewHolder = new SingleUtilityViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_multiple, parent, false);

            viewHolder = new MultipleUtilityViewHolder(view);
        }
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            ((SingleUtilityViewHolder)holder).itemView.setOnClickListener(v ->
                    listener.onClick(holder.itemView, items.get(position)));
            ((SingleUtilityViewHolder) holder).bind(items.get(position));
        }
        else {
            ((MultipleUtilityViewHolder)holder).itemView.setOnClickListener(v ->
                    listener.onClick(holder.itemView, items.get(position)));
            ((MultipleUtilityViewHolder)holder).bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isSingleInLine()) return 1;
        else return 2;

    }

}
