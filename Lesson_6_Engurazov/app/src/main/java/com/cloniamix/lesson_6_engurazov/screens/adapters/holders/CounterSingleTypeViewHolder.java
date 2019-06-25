package com.cloniamix.lesson_6_engurazov.screens.adapters.holders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment2;
import com.cloniamix.lesson_6_engurazov.POJO.Counter;
import com.cloniamix.lesson_6_engurazov.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CounterSingleTypeViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageViewIcon;
    private TextView textViewCounterName;
    private TextView textViewCounterNumber;
    private EditText editTextNewReadings;
    private ImageView imageViewSend;
    private TextView textViewAlarmHint;
    private ImageView imageViewInfo;
    private ImageView imageViewMore;

    public CounterSingleTypeViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        textViewCounterName = itemView.findViewById(R.id.textViewCounterName);
        textViewCounterNumber = itemView.findViewById(R.id.textViewCounterNumber);
        editTextNewReadings = itemView.findViewById(R.id.editTextNewReadings);
        imageViewSend = itemView.findViewById(R.id.imageViewSend);
        textViewAlarmHint = itemView.findViewById(R.id.textViewAlarmHint);
        imageViewInfo = itemView.findViewById(R.id.imageViewInfo);
        imageViewMore = itemView.findViewById(R.id.imageViewMore);
    }

    public void bind(Counter counter, Fragment2.OnFragment2InteractionListener listener){
        imageViewSend.setOnClickListener(v -> listener.onSendCounterReadingsClick(itemView,getCounterReadings()));
        imageViewInfo.setOnClickListener(v -> listener.onInfoItemClick());
        imageViewMore.setOnClickListener(v -> listener.onMoreItemClick(itemView));

        imageViewIcon.setImageResource(counter.getIconResId());
        textViewCounterName.setText(counter.getCounterName());
        textViewCounterNumber.setText(counter.getCounterNumber());

        if (counter.isAlarm()){
            textViewAlarmHint.setCompoundDrawables(itemView.getResources().getDrawable(R.drawable.ic_alert)
                        ,null
                        ,null
                        ,null);
            /*textViewAlarmHint.setCompoundDrawablePadding(7);*/
            textViewAlarmHint.setTextColor(itemView.getResources().getColor(R.color.colorCoral));
            textViewAlarmHint.setText(counter.getAlarmText());

        } else {
            textViewAlarmHint.setText(counter.getAlarmText());
        }
    }

    private String getCounterReadings(){
        return editTextNewReadings.getText().toString();
    }
}
