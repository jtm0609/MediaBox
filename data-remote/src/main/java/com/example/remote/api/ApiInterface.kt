package com.example.remote.api

import com.example.remote.model.ImagesResponse
import com.example.remote.model.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ImagesResponse

    @GET("vclip")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VideosResponse
}