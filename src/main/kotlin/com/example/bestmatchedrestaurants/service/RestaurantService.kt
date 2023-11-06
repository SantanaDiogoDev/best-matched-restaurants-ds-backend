package com.example.bestmatchedrestaurants.service

import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantService(val repository: IRestaurantRepository) {
    fun findAll(): Iterable<RestaurantModel> = repository.findAll();
    fun findByFilter(restaurant: RestaurantFilter): Iterable<RestaurantModel> = repository.findAll();
}