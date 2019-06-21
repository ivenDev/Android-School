package com.cloniamix.lesson_5_engurazov.screens.screen6.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloniamix.lesson_5_engurazov.POJO.ProposedService;
import com.cloniamix.lesson_5_engurazov.R;
import com.cloniamix.lesson_5_engurazov.screens.screen6.utils.MyClickListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyHolder> {

    private ArrayList<ProposedService> services;
    private MyClickListener listener;


    public ServicesAdapter(ArrayList<ProposedService> services, MyClickListener listener) {
        this.services = services;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_servise_item, parent, false);

        return new MyHolder(v);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> listener.onClick(holder.itemView, services.get(position)));
        holder.bind(services.get(position));
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewIcon;
        private ImageView imageViewMore;
        private TextView textViewServiceName;
        private TextView textViewServiceContent;
        private TextView textViewServiceAddress;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            imageViewMore = itemView.findViewById(R.id.imageViewMore);
            textViewServiceName = itemView.findViewById(R.id.textViewServiceName);
            textViewServiceContent = itemView.findViewById(R.id.textViewServiceContent);
            textViewServiceAddress = itemView.findViewById(R.id.textViewServiceAddress);
        }

        public void bind(ProposedService service){

            textViewServiceName.setText(service.getName());
            textViewServiceContent.setText(service.getContent());
            textViewServiceAddress.setText(service.getAddress());
            Glide.with(itemView)
                    .load(service.getImageUrl())
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(itemView.getResources().getDrawable(R.drawable.ic_crop))
                    .into(imageViewIcon);

        }
    }
}
