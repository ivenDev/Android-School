package com.cloniamix.lesson_6_engurazov.screens.adapters.holders;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment2;
import com.cloniamix.lesson_6_engurazov.POJO.Counter;
import com.cloniamix.lesson_6_engurazov.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CounterViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageViewIcon;
    private TextView textViewCounterName;
    private TextView textViewCounterNumber;
    private EditText editTextMorningReadings;
    private EditText editTextNightReadings;
    private EditText editTextPeakReadings;
    private ImageButton imageButtonViewSend;
    private TextView textViewAlarmHint;
    private ImageView imageViewInfo;
    private ImageView imageViewMore;

    private TextInputLayout textInputLayoutMorningReadings;
    private TextInputLayout textInputLayoutNightReadings;
    private TextInputLayout textInputLayoutPeakReadings;

    public CounterViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        textViewCounterName = itemView.findViewById(R.id.textViewCounterName);
        textViewCounterNumber = itemView.findViewById(R.id.textViewCounterNumber);
        editTextMorningReadings = itemView.findViewById(R.id.editTextMorningReadings);
        editTextNightReadings = itemView.findViewById(R.id.editTextNightReadings);
        editTextPeakReadings = itemView.findViewById(R.id.editTextPeakReadings);
        imageButtonViewSend = itemView.findViewById(R.id.imageButtonViewSend);
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
            imageButtonViewSend.setOnClickListener(v -> listener.onSendCounterReadingsClick(itemView, getCounterReading()));
        } else {
            imageButtonViewSend.setOnClickListener(v -> listener.onSendCounterMorningNightPeakReadingsClick(itemView, getCounterReadingsList()));
        }


        imageViewInfo.setOnClickListener(v -> listener.onInfoItemClick());
        imageViewMore.setOnClickListener(v -> listener.onMoreItemClick(itemView));

        imageViewIcon.setImageResource(counter.getIconResId());
        textViewCounterName.setText(counter.getCounterName());
        textViewCounterNumber.setText(counter.getCounterNumber());

        if (counter.isAlarm()){
            textViewAlarmHint.setCompoundDrawablesWithIntrinsicBounds(itemView.getResources().getDrawable(R.drawable.ic_alert)
                    ,null
                    ,null
                    ,null);
            textViewAlarmHint.setCompoundDrawablePadding(7);
            textViewAlarmHint.setTextColor(itemView.getResources().getColor(R.color.colorCoral));
            String alarmText = itemView.getResources().getString(R.string.alarm_text)
                    + " "
                    + counter.getAlarmDay();
            textViewAlarmHint.setText(alarmText);

        } else {
            String text = itemView.getResources().getString(R.string.readings_passed_alarm_text)
                    + " "
                    + counter.getReadingPassedDay()
                    + " "
                    + itemView.getResources().getString(R.string.calculation_alarm_text)
                    + " "
                    + counter.getAlarmDay();

            SpannableString strText = new SpannableString(text);

            int firstStartBold = itemView.getResources().getString(R.string.readings_passed_alarm_text).length()
                    + 1;
            int firstEndBold = firstStartBold + counter.getReadingPassedDay().length();


            int secondStartBold = firstEndBold
                    + itemView.getResources().getString(R.string.calculation_alarm_text).length()
                    + 1;
            int secondEndBold = secondStartBold + counter.getAlarmDay().length();


            strText.setSpan(new StyleSpan(Typeface.BOLD)
                    ,firstStartBold
                    ,firstEndBold
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            strText.setSpan(new StyleSpan(Typeface.BOLD)
                    ,secondStartBold
                    ,secondEndBold
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textViewAlarmHint.setText(strText);
        }
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

