package com.cloniamix.lesson_6_engurazov.screens.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cloniamix.lesson_6_engurazov.R;
import com.cloniamix.lesson_6_engurazov.screens.MainActivity;

public class Fragment3 extends Fragment {

    private static final String TAG = "My tag";

    private Button buttonShowBanner;
    private Button buttonHideBanner;

    private MainActivity context;


    public static Fragment3 newInstance() {
        return new Fragment3();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        buttonShowBanner = view.findViewById(R.id.buttonShowBanner);
        buttonShowBanner.setOnClickListener(v -> showPagerFragment());

        buttonHideBanner = view.findViewById(R.id.buttonHideBanner);
        buttonHideBanner.setOnClickListener(v -> hidePagerFragment());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = (MainActivity) context;
    }

    private void showPagerFragment(){

        buttonShowBanner.setVisibility(View.GONE);
        buttonHideBanner.setVisibility(View.VISIBLE);

        context.getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutPagerFragmentContainer, PagerFragment.newInstance(), TAG)
                .commit();
    }

    private void hidePagerFragment(){

        PagerFragment pagerFragment =(PagerFragment) context.getSupportFragmentManager().findFragmentByTag(TAG);

        buttonShowBanner.setVisibility(View.VISIBLE);
        buttonHideBanner.setVisibility(View.GONE);

        if (pagerFragment != null){
            context.getSupportFragmentManager().beginTransaction()
                    .remove(pagerFragment)
                    .commit();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

}
