package com.example.dogapp.views.home.uistate

data class HomeViewUIState (
    val error: Throwable? = null,
    val selectedBredName: String? = null,
    val breedImages: HashMap<String,ArrayList<String>> = hashMapOf(),
    var isLoading: Boolean = false
)