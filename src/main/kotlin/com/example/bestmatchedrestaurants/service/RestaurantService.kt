package com.example.bestmatchedrestaurants.service

import com.example.bestmatchedrestaurants.model.CuisineModel
import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantService(val repository: IRestaurantRepository) {
    fun findAll(): Iterable<RestaurantModel> = repository.findAll();
    fun findByFilter(restaurant: RestaurantFilter): Iterable<RestaurantModel> = repository.findAll();

    fun readRestaurantCsv(){
        val restaurantList = mutableListOf<RestaurantModel>();
        val restaurantFile = this::class.java.getResource("/static/restaurants.csv").readText();
        val lines = restaurantFile.lines();
        for(i in 1 until lines.size){
            val values = lines[i].split(",");
            val name = values[0];
            val customer_rating = values[1].toInt();
            val distance = values[2].toInt();
            val price = values[3].toInt();
            val cuisine_id = values[4].toInt();
            restaurantList.add(RestaurantModel(name, customer_rating, distance, price, cuisine_id));
        }
    }
}