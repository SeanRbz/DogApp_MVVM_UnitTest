package com.example.dogapp.repository.remote

import com.example.dogapp.repository.entities.DogBreedListResponse
import com.example.dogapp.repository.entities.RandomBreedImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogServiceAPI {

    @GET("api/breeds/list")
    suspend fun getAllBreeds(): DogBreedListResponse

    @GET("api/breed/{breed}/images")
    suspend fun getBreedImageList(@Path("breed") breedName: String): DogBreedListResponse

    @GET("api/breed/{breed}/images/random")
    suspend fun getBreedRandomImage(@Path("breed") breedName: String): RandomBreedImageResponse

}