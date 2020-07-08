package com.axxess.codechallenge.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.axxess.codechallenge.gallery_datasource.GalleryDataSourceClass;
import com.axxess.codechallenge.gallery_datasource.GalleryDataSourceFactory;
import com.axxess.codechallenge.util.GalleryView;
import com.axxess.codechallenge.util.Repository;


import io.reactivex.disposables.CompositeDisposable;


public class GalleryViewModel extends ViewModel {

    private GalleryDataSourceFactory galleryDataSourceFactory;
    private LiveData<PagedList<GalleryView>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Repository repository;

    public GalleryViewModel(Repository repository) {
        this.repository = repository;
    }


    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();

        listLiveData = new LivePagedListBuilder<>(galleryDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(galleryDataSourceFactory.getMutableLiveData(), GalleryDataSourceClass::getProgressLiveStatus);

    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<GalleryView>> getListLiveData(String searchParam) {
        galleryDataSourceFactory = new GalleryDataSourceFactory(repository, compositeDisposable, searchParam);
        initializePaging();
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}