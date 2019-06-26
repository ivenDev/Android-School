package com.cloniamix.lesson_6_engurazov.screens.adapters.holders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment2;
import com.cloniamix.lesson_6_engurazov.POJO.Counter;
import com.cloniamix.lesson_6_engurazov.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CounterMultiTypeViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageViewIcon;
    private TextView textViewCounterName;
    private TextView textViewCounterNumber;
    private EditText editTextMorningReadings;
    private EditText editTextNightReadings;
    private EditText editTextPeakReadings;
    private ImageView imageViewSend;
    private TextView textViewAlarmHint;
    private ImageView imageViewInfo;
    private ImageView imageViewMore;

    private TextInputLayout textInputLayoutMorningReadings;
    private TextInputLayout textInputLayoutNightReadings;
    private TextInputLayout textInputLayoutPeakReadings;

    public CounterMultiTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        textViewCounterName = itemView.findViewById(R.id.textViewCounterName);
        textViewCounterNumber = itemView.findViewById(R.id.textViewCounterNumber);
        editTextMorningReadings = itemView.findViewById(R.id.editTextMorningReadings);
        editTextNightReadings = itemView.findViewById(R.id.editTextNightReadings);
        editTextPeakReadings = itemView.findViewById(R.id.editTextPeakReadings);
        imageViewSend = itemView.findViewById(R.id.imageViewSend);
        textViewAlarmHint = itemView.findViewById(R.id.textViewAlarmHint);
        imageViewInfo = itemView.findViewById(R.id.imageViewInfo);
        imageViewMore = itemView.findViewById(R.id.imageViewMore);

        textInputLayoutMorningReadings = itemView.findViewById(R.id.textInputLayoutMorningReadings);
        textInputLayoutNightReadings = itemView.findViewById(R.id.textInputLayoutNightReadings);
        textInputLayoutPeakReadings = itemView.findViewById(R.id.textInputLayoutPeakReadings);
    }

    public void bind(Counter counter, Fragment2.OnFragment2InteractionListener listener){

        if (counter.isSingleType()){
            textInputLayoutNightReadings.setVisibility(View.GONE);
            textInputLayoutPeakReadings.setVisibility(View.GONE);

            textInputLayoutMorningReadings.setHint(itemView.getResources().getString(R.string.new_readings_hint_text));
            imageViewSend.setOnClickListener(v -> listener.onSendCounterReadingsClick(itemView, getCounterReading()));
        } else {
            imageViewSend.setOnClickListener(v -> listener.onSendCounterMorningNightPeakReadingsClick(itemView, getCounterReadingsList()));
        }


        imageViewInfo.setOnClickListener(v -> listener.onInfoItemClick());
        imageViewMore.setOnClickListener(v -> listener.onMoreItemClick(itemView));

        imageViewIcon.setImageResource(counter.getIconResId());
        textViewCounterName.setText(counter.getCounterName());
        textViewCounterNumber.setText(counter.getCounterNumber());
        textViewAlarmHint.setText(counter.getAlarmText());
    }

    private String[] getCounterReadingsList(){

        return new String[]{editTextMorningReadings.getText().toString()
                ,editTextNightReadings.getText().toString()
                ,editTextPeakReadings.getText().toString()
        };
    }

    private String getCounterReading(){
        return editTextMorningReadings.getText().toString();
    }
}

