package com.example.data.api

import com.example.data.model.ImagesResponse
import com.example.data.model.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ImagesResponse

    @GET("vclip")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VideosResponse
}