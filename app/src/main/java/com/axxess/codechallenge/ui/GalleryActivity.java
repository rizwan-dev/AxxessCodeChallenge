package com.axxess.codechallenge.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.axxess.codechallenge.MyApplication;
import com.axxess.codechallenge.R;
import com.axxess.codechallenge.databinding.ViewGalleryBinding;
import com.axxess.codechallenge.util.AxxessUtil;
import com.axxess.codechallenge.util.Constant;
import com.axxess.codechallenge.util.GalleryView;
import com.axxess.codechallenge.util.OnItemClickListener;
import com.axxess.codechallenge.util.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;


public class GalleryActivity extends AppCompatActivity implements OnItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;

    GalleryViewModel viewModel;

    ViewGalleryBinding binding;
    private GalleryViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.view_gallery);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);
        init();
    }

    private void init() {

        binding.list.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new GalleryViewAdapter(this);
        binding.list.setAdapter(adapter);

        if (!Constant.checkInternetConnection(this)) {
            Snackbar.make(findViewById(android.R.id.content), Constant.CHECK_NETWORK_ERROR, Snackbar.LENGTH_SHORT)
                    .show();
        }


        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constant.LOADING)) {
                binding.progress.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(Constant.LOADED)) {
                binding.progress.setVisibility(View.GONE);
            }
        });

    }

    public void searchClicked(View view) {

        String searchParam = binding.edtSearch.getText().toString().trim();
        if (searchParam.isEmpty()) {
            Toast.makeText(this, "Please enter text to search", Toast.LENGTH_SHORT).show();
            return;
        }
        AxxessUtil.hideKeyBoard(this, view);
        viewModel.getListLiveData(searchParam).observe(this, adapter::submitList);
    }


    @Override
    public void onItemClick(GalleryView item) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constant.DATA, item);
        startActivity(intent);
        Toast.makeText(this, "Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
