package com.example.bestmatchedrestaurants.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
data class RestaurantModel(
    @Id
    var name: String,
    var customer_rating: Int,
    var distance: Int,
    var price: Int,
    @ManyToOne
    var cuisine: CuisineModel
)