package com.example.dogapp.views.home

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.repository.usecases.GetAllBreedUseCase
import com.example.dogapp.repository.usecases.GetDogBreedImageUseCase
import com.example.dogapp.repository.usecases.GetDogBreedRandomImageUseCase
import com.example.dogapp.utils.Result
import com.example.dogapp.views.home.uistate.HomeViewUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val allBreedUseCase: GetAllBreedUseCase,
    val dogBreedImageUseCase: GetDogBreedImageUseCase,
    val dogBreedRandomImageUseCase: GetDogBreedRandomImageUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<HomeViewUIState> = MutableStateFlow(HomeViewUIState())
    val uiState: StateFlow<HomeViewUIState> = _uiState.asStateFlow()

    fun getAllDogBreeds() {
        setLoading(true)
        viewModelScope.launch {
            allBreedUseCase(Unit).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        val hash : HashMap<String,ArrayList<String>> = hashMapOf()
                        result.data.message.forEach {
                            hash[it] = arrayListOf()
                        }
                        _uiState.update {
                            it.copy(breedImages = hash, isLoading = false)
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(breedImages = hashMapOf(), error = result.exception)
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    fun getDogBreedImage(breedName: String) {
        setLoading(true)
        var hasImageAlready = false
        _uiState.value.breedImages.get(breedName)?.let {
            if(it.size>0) hasImageAlready = true
        }
        if(!hasImageAlready) {
            viewModelScope.launch {
                dogBreedImageUseCase(breedName).collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.value.breedImages.get(breedName)?.apply {
                                this.addAll(result.data.message)
                            }
                            _uiState.update {
                                it.copy(isLoading = false, selectedBredName = breedName)
                            }
                        }

                        is Result.Error -> {

                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    fun getDogBreedRandomImage() {
        setLoading(true)
        viewModelScope.launch {
            dogBreedRandomImageUseCase("").collectLatest { result ->
                when (result) {
                    is Result.Success -> {

                    }

                    is Result.Error -> {

                    }

                    else -> {

                    }
                }
            }
        }
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading, error = null, selectedBredName = null)
        }
    }

}