package com.cloniamix.lesson_6_engurazov.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cloniamix.lesson_6_engurazov.R;
import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment1;
import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment2;
import com.cloniamix.lesson_6_engurazov.screens.fragments.Fragment3;
import com.cloniamix.lesson_6_engurazov.screens.fragments.ImageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements
        Fragment1.OnFragment1InteractionListener
        , Fragment2.OnFragment2InteractionListener
        , ImageFragment.OnImageFragmentInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment1();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
            case R.id.navigation_item_one:
                showFragment1();
                return true;
            case R.id.navigation_item_two:
                showFragment2();
                return true;
            case R.id.navigation_item_three:
                showFragment3();
                return true;
        }
            return false;

        });
    }

    private void showFragment1(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutFragmentContainer, Fragment1.newInstance())
                .commit();
    }

    private void showFragment2(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutFragmentContainer, Fragment2.newInstance())
                .commit();
    }

    private void showFragment3(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutFragmentContainer, Fragment3.newInstance())
                .commit();
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title_text))
                .setMessage(getString(R.string.dialog_message_text))
                .setPositiveButton(getString(R.string.dialog_positive_button_text)
                        , (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void onMenuItemClicked(String message) {
        showToast(message);
    }

    @Override
    public void onSendCounterReadingsClick(View view, String counterReadings) {
        String message;
        if (counterReadings.equals("")){
            message = getString(R.string.incorrect_readings_toast_text);
        }else {
            message = counterReadings;
        }
        showToast(message);
    }

    @Override
    public void onSendCounterMorningNightPeakReadingsClick(View view, String[] readings) {
        String message;
        if (readings.length!=3 || readings[0].equals("") || readings[1].equals("") || readings[2].equals("")){
            message = getString(R.string.incorrect_readings_toast_text);
        } else {
            message = getString(R.string.morning_night_peak_readings_toast_text, readings[0],readings[1],readings[2]);
        }
        showToast(message);
    }

    @Override
    public void onMoreItemClick(View view) {
        showSnackbar(view, getString(R.string.more_item_toast_text));
    }

    @Override
    public void onInfoItemClick() {
        showDialog();
    }

    @Override
    public void onLampItemClicked() {
        showToast(getString(R.string.lamp_item_toast_text));
    }


    @Override
    public void onImageClick(String text) {
        showToast(text);
    }
}

