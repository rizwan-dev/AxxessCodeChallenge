package com.axxess.codechallenge.util;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.axxess.codechallenge.ui.GalleryViewModel;

import javax.inject.Inject;



public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GalleryViewModel.class)) {
            return (T) new GalleryViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
