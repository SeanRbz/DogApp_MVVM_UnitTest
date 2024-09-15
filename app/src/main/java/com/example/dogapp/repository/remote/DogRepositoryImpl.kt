package com.example.dogapp.repository.remote

import com.example.dogapp.di.qualifiers.IoDispatcher
import com.example.dogapp.repository.entities.DogBreedListResponse
import com.example.dogapp.repository.entities.RandomBreedImageResponse
import com.example.dogapp.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DogServiceRepository {

    fun getAllDogBreeds(): Flow<Result<DogBreedListResponse>>

    fun getBreedImageList(breedName: String): Flow<Result<DogBreedListResponse>>

    fun getBreedRandomImage(breedName: String): Flow<Result<RandomBreedImageResponse>>

}

class DogRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatchersIO: CoroutineDispatcher,
    private val api: DogServiceAPI
) : DogServiceRepository {

    override fun getAllDogBreeds(): Flow<Result<DogBreedListResponse>> =
        flow<Result<DogBreedListResponse>> {
            val response = api.getAllBreeds()
            emit(Result.Success(response))
        }.catch { emit(Result.Error(it)) }.flowOn(dispatchersIO)


    override fun getBreedImageList(breedName: String): Flow<Result<DogBreedListResponse>> =
        flow<Result<DogBreedListResponse>> {
            val response = api.getBreedImageList(breedName = breedName)
            emit(Result.Success(response))
        }.catch { emit(Result.Error(it)) }.flowOn(dispatchersIO)

    override fun getBreedRandomImage(breedName: String): Flow<Result<RandomBreedImageResponse>> =
        flow<Result<RandomBreedImageResponse>> {
            val response = api.getBreedRandomImage(breedName = breedName)
            emit(Result.Success(response))
        }.catch { emit(Result.Error(it)) }.flowOn(dispatchersIO)
}