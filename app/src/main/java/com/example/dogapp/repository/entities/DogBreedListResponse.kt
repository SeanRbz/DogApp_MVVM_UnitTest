package com.example.dogapp.repository.entities

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DogBreedListResponse (
    val message: List<String>,
    override val status: String
): BaseEntity
