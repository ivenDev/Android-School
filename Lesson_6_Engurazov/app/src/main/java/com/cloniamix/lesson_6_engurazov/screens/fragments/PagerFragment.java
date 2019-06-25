package com.cloniamix.lesson_6_engurazov.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloniamix.lesson_6_engurazov.POJO.PagerData;
import com.cloniamix.lesson_6_engurazov.R;
import com.cloniamix.lesson_6_engurazov.screens.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PagerFragment extends Fragment {

    private MainActivity context;


    static PagerFragment newInstance() {
        return new PagerFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            ,@Nullable ViewGroup container
            ,@Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.viewPagerFragmentContainer);
        PagerAdapter pagerAdapter = new MyFragmentStatePagerAdapter(context.getSupportFragmentManager()
                ,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        context = null;
    }


    private ArrayList<PagerData> getPagerData(){
        ArrayList<PagerData> list = new ArrayList<>();
        list.add(new PagerData(R.drawable.img_1, getString(R.string.picture_1_text)));
        list.add(new PagerData(R.drawable.img_2, getString(R.string.picture_2_text)));
        list.add(new PagerData(R.drawable.img_3, getString(R.string.picture_3_text)));

        return list;
    }

    public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        MyFragmentStatePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            PagerData pagerData = getPagerData().get(position);
            return ImageFragment.newInstance(pagerData.getImageResId(), pagerData.getText());
        }

        @Override
        public int getCount() {
            return getPagerData().size();
        }

    }

}
