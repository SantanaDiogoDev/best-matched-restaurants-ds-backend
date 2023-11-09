package com.example.bestmatchedrestaurants.utils

import com.example.bestmatchedrestaurants.repository.ICuisineRepository
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataBaseInitializer(private val ICuisineRepository: ICuisineRepository, private val restaurantRepository: IRestaurantRepository) : CommandLineRunner{

    override fun run(vararg args: String?){
        val cuisinePath = "/static/cuisines.csv";
        val restaurantPath = "/static/restaurants.csv";

        val csvReader = CsvReader(ICuisineRepository);
        val cuisines = csvReader.readCuisiceCsv(cuisinePath);
        ICuisineRepository.saveAll(cuisines);

        val restaurants = csvReader.readRestaurantCsv(restaurantPath);
        restaurantRepository.saveAll(restaurants);
    }


}