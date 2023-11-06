package com.example.bestmatchedrestaurants.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class CuisineModel(
    @Id
    var id: Int,
    var name: String
)
