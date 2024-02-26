package com.example.typespeed;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("get?number=1")
    Single<TextResponse> loadText();
}
