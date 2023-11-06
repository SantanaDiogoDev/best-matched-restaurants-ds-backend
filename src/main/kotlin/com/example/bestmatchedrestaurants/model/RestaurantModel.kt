package com.example.bestmatchedrestaurants.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class RestaurantModel (
    @Id
    var name: String,
    var customer_rating: Int,
    var distance: Int,
    var price: Int,
    var cuisine_id: Int
)