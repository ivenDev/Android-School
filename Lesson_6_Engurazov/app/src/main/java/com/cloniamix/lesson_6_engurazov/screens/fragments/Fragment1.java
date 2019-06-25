package com.cloniamix.lesson_6_engurazov.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloniamix.lesson_6_engurazov.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    private OnFragment1InteractionListener listener;


    public static Fragment1 newInstance() {
        return new Fragment1();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_1, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbarFragment1 = view.findViewById(R.id.toolbarFragment1);
        toolbarFragment1.inflateMenu(R.menu.menu_toolbar_fragment_1);

        toolbarFragment1.getMenu().findItem(R.id.menu_item_search).setOnMenuItemClickListener(item -> {
                listener.onMenuItemClicked(getString(R.string.search_item_toast_text));
                return true;
        });
        toolbarFragment1.getMenu().findItem(R.id.menu_item_action_2).setOnMenuItemClickListener(item -> {
                listener.onMenuItemClicked(getString(R.string.action_2_item_toast_text));
                return true;
        });
        toolbarFragment1.getMenu().findItem(R.id.menu_item_action_3).setOnMenuItemClickListener(item -> {
                listener.onMenuItemClicked(getString(R.string.action_3_item_toast_text));
                return true;
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1InteractionListener) {
            listener = (OnFragment1InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragment1InteractionListener {
        void onMenuItemClicked(String message);
    }
}
