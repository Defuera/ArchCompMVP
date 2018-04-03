package ru.justd.abnamro.app.model.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single


interface VenueApi {

    @GET("venues/search")
    fun search(
            @Query("near") near: String,
            @Query("query") query: String
    ): Single<ApiResponseWrapper>

}