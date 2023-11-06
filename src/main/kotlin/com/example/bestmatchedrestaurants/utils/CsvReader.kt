package com.example.bestmatchedrestaurants.utils

import com.example.bestmatchedrestaurants.model.CuisineModel
import com.example.bestmatchedrestaurants.model.RestaurantModel

class CsvReader {
    fun readCuisiceCsv() : List<CuisineModel>{
        val cuisineList = mutableListOf<CuisineModel>();
        val cuisineFile = this::class.java.getResource("/static/cuisines.csv").readText();
        val lines = cuisineFile.lines();
        for(i in 1 until lines.size){
            val values = lines[i].split(",");
            val id = values[0].toInt();
            val name = values[1];
            cuisineList.add(CuisineModel(id, name));
        }
        return cuisineList;
    }

    fun readRestaurantCsv(): List<RestaurantModel>{
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
        return restaurantList;
    }
}