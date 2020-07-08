package com.axxess.codechallenge.ui;


import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.axxess.codechallenge.R;
import com.axxess.codechallenge.databinding.RowLayoutBinding;
import com.axxess.codechallenge.util.GalleryView;
import com.axxess.codechallenge.util.OnItemClickListener;


public class GalleryViewAdapter extends PagedListAdapter<GalleryView, GalleryViewAdapter.MyViewHolder> {

    OnItemClickListener listener;

    GalleryViewAdapter(OnItemClickListener listener) {
        super(GalleryView.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.row_layout, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.binding.setModel(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RowLayoutBinding binding;

        MyViewHolder(RowLayoutBinding itemView) {
            super(itemView.getRoot());
            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getItem(getLayoutPosition()));
                }
            });
            binding = itemView;

        }
    }
}
