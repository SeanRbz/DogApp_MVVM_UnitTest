package com.example.dogapp.repository.entities

import kotlinx.serialization.Serializable

@Serializable
class RandomBreedImageResponse (
    val message: String,
    override val status: String
): BaseEntity
