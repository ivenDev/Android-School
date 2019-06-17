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

import static com.cloniamix.lesson_4_engurazov.utils.Utils.ITEM_MULTIPLE;
import static com.cloniamix.lesson_4_engurazov.utils.Utils.ITEM_SINGLE;

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

        if (viewType == ITEM_SINGLE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_single, parent, false);

            viewHolder = new SingleUtilityViewHolder(view);
        } else {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_multiple, parent, false);
            viewHolder = new MultipleUtilityViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_SINGLE) {
            ((SingleUtilityViewHolder) holder).itemView.setOnClickListener(v ->
                    listener.onClick(holder.itemView, items.get(position)));
            ((SingleUtilityViewHolder) holder).bind(items.get(position));
        } else {
            ((MultipleUtilityViewHolder) holder).itemView.setOnClickListener(v ->
                    listener.onClick(holder.itemView, items.get(position)));
            ((MultipleUtilityViewHolder) holder).bind(items.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isSingleInLine()) return ITEM_SINGLE;
        else if (position % 2 == 0) {
            if (!(getItemViewType(position + 1) == ITEM_MULTIPLE)) return ITEM_SINGLE;
        }
        return ITEM_MULTIPLE;


    }
}
