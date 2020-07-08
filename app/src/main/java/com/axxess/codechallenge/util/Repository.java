package com.axxess.codechallenge.util;

import com.google.gson.JsonElement;

import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }


    /*
     * method to call gallery api
     * */
    public Observable<JsonElement> executeGalleryApi(int page, String queryString) {
        return apiCallInterface.fetchGallery(Constant.TOKEN, page, queryString);
    }

}
