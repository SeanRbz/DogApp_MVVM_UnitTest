package com.example.dogapp

import com.example.dogapp.repository.entities.DogBreedListResponse
import com.example.dogapp.repository.remote.DogRepositoryImpl
import com.example.dogapp.repository.remote.DogServiceAPI
import com.example.dogapp.repository.usecases.GetAllBreedUseCase
import com.example.dogapp.utils.Result
import com.example.dogapp.utils.data
import com.example.dogapp.utils.succeeded
import io.reactivex.Observer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var userRepository: DogRepositoryImpl

    @Before
    fun setUp() {
        userRepository = mock()
    }

    @Test
    fun getDogBreedList() = runBlocking {
        // Arrange
        val mockResponse = DogBreedListResponse(
            listOf(
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_10185.jpg",
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_10437.jpg",
                "https://images.dog.ceo/breeds/affenpinscher/n02110627_3841.jpg",
            ), "Success"
        )
        val result = Result.Success(mockResponse)
        val flow: Flow<Result<DogBreedListResponse>> = flowOf(result)

        whenever(userRepository.getAllDogBreeds()).thenReturn(flow)

        // Act
        val resultFlow = userRepository.getAllDogBreeds()

        // Assert
        resultFlow.collect { result ->
           assert(result.data!=null)
           assertEquals(mockResponse.message, result.data?.message)
        }
    }
}