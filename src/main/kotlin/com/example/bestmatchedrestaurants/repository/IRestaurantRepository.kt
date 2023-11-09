package com.example.bestmatchedrestaurants.repository

import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IRestaurantRepository : JpaRepository<RestaurantModel, String> {
}