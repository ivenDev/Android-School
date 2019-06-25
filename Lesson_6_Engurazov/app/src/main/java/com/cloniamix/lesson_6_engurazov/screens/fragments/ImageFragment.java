package com.cloniamix.lesson_6_engurazov.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloniamix.lesson_6_engurazov.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {
    private static final String ARG_IMAGE_RES_ID = "param1";
    private static final String ARG_TEXT = "param2";

    private int imageResId;
    private String text;

    private OnImageFragmentInteractionListener listener;

    public ImageFragment() {
    }

    static ImageFragment newInstance(int imageResId, String text) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
            text = getArguments().getString(ARG_TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_image, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> listener.onImageClick(text));

        TextView textViewPageName = view.findViewById(R.id.textViewPageName);
        textViewPageName.setText(text);

        Glide.with(view)
                .load(imageResId)
                .centerCrop()
                .into(imageView);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnImageFragmentInteractionListener) {
            listener = (OnImageFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPagerFragmentInteractionListener");
        }
    }

    public interface OnImageFragmentInteractionListener {
        void onImageClick(String text);
    }

}
