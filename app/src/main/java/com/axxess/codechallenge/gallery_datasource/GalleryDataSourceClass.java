package com.axxess.codechallenge.gallery_datasource;

import android.annotation.SuppressLint;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.axxess.codechallenge.util.Constant;
import com.axxess.codechallenge.util.GalleryView;
import com.axxess.codechallenge.util.Repository;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class GalleryDataSourceClass extends PageKeyedDataSource<Integer, GalleryView> {

    private Repository repository;
    private Gson gson;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;
    private String searchParam;

    GalleryDataSourceClass(Repository repository, CompositeDisposable compositeDisposable, String searchParam) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
        this.searchParam = searchParam;
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = builder.setLenient().create();
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, GalleryView> callback) {

        repository.executeGalleryApi(sourceIndex, searchParam)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("data");

                            ArrayList<GalleryView> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                String imageUrl = null;
                                JSONObject jsonObject = array.getJSONObject(i);
                                if (jsonObject.has("images")) {
                                    imageUrl = jsonObject.getJSONArray("images").getJSONObject(0).optString("link");
                                } else {
                                    imageUrl = jsonObject.optString("link");
                                }
                                arrayList.add(new GalleryView(jsonObject.optString("id"),
                                        jsonObject.optString("title"), imageUrl));
                            }

                            sourceIndex++;
                            callback.onResult(arrayList, null, sourceIndex);
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, GalleryView> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, GalleryView> callback) {

        repository.executeGalleryApi(params.key, searchParam)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("data");

                            ArrayList<GalleryView> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                String imageUrl = null;
                                JSONObject jsonObject = array.getJSONObject(i);
                                if (jsonObject.has("images")) {
                                    imageUrl = jsonObject.getJSONArray("images").getJSONObject(0).optString("link");
                                } else {
                                    imageUrl = jsonObject.optString("link");
                                }
                                arrayList.add(new GalleryView(jsonObject.optString("id"),
                                        jsonObject.optString("title"), imageUrl));
                            }

                            callback.onResult(arrayList,  params.key + 1);

                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );
    }
}
