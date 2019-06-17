package com.cloniamix.lesson_4_engurazov.screens.adapter.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloniamix.lesson_4_engurazov.pojo.InfoItem;
import com.cloniamix.lesson_4_engurazov.R;

import androidx.recyclerview.widget.RecyclerView;

public class SingleUtilityViewHolder extends RecyclerView.ViewHolder{


    private ImageView imageViewIcon;
    private TextView textViewItemName;
    private TextView textViewItemHint;

    public SingleUtilityViewHolder(View v) {
        super(v);
        imageViewIcon = v.findViewById(R.id.imageViewIcon);
        textViewItemName = v.findViewById(R.id.textViewItemName);
        textViewItemHint = v.findViewById(R.id.textViewItemHint);


    }

    public void bind(InfoItem infoItem) {
        imageViewIcon.setImageResource(infoItem.getIconResId());
        textViewItemName.setText(infoItem.getName());
        textViewItemHint.setVisibility(View.GONE);
    }
}

