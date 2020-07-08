package com.axxess.codechallenge.gallery_datasource;



import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.axxess.codechallenge.util.GalleryView;
import com.axxess.codechallenge.util.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class GalleryDataSourceFactory extends DataSource.Factory<Integer, GalleryView> {

    private MutableLiveData<GalleryDataSourceClass> liveData;
    private Repository repository;
    private CompositeDisposable compositeDisposable;
    private String searchParam;

    public GalleryDataSourceFactory(Repository repository, CompositeDisposable compositeDisposable, String searchParam) {
        this.repository = repository;
        this.searchParam = searchParam;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<GalleryDataSourceClass> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, GalleryView> create() {
        GalleryDataSourceClass dataSourceClass = new GalleryDataSourceClass(repository, compositeDisposable, searchParam);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
