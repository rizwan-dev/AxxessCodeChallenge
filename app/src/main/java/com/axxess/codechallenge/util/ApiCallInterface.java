package com.axxess.codechallenge.util;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET(Urls.FetchGallery)
    Observable<JsonElement> fetchGallery(@Header("Authorization") String token,
                                         @Path("page") int page,
                                         @Query("q") String query);

}
