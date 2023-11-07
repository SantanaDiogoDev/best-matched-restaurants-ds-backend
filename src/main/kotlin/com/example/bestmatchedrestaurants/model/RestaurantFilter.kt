package com.example.bestmatchedrestaurants.model

data class RestaurantFilter(
    var name: String?,
    var customerRating: Int?,
    var distance: Int?,
    var price: Int?,
    var cuisine: String?
)
