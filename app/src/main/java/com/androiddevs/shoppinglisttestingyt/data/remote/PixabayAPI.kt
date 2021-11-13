package com.androiddevs.shoppinglisttestingyt.data.remote

import androidx.core.os.BuildCompat
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = "24319822-b136850c20a8d393cb9d1ad15"
    ): Response<ImageResponse>
}