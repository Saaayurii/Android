package com.example.booksapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search.json")
    Call<SearchResponse> searchBooks(
            @Query("q") String query,
            @Query("limit") int limit
    );
}
