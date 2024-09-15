package com.example.dogapp.repository.usecases

import com.example.dogapp.di.qualifiers.IoDispatcher
import com.example.dogapp.repository.entities.DogBreedListResponse
import com.example.dogapp.repository.remote.DogRepositoryImpl
import com.example.dogapp.utils.FlowUseCase
import com.example.dogapp.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllBreedUseCase @Inject constructor(
    private val repository: DogRepositoryImpl,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): FlowUseCase<Unit, DogBreedListResponse>(ioDispatcher) {

    override fun execute(parameters: Unit): Flow<Result<DogBreedListResponse>> {
        return repository.getAllDogBreeds()
    }
}