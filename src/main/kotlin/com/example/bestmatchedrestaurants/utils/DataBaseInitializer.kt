package com.example.bestmatchedrestaurants.utils

import com.example.bestmatchedrestaurants.repository.ICuisineRepository
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataBaseInitializer(private val cuisineRepository: ICuisineRepository, private val restaurantRepository: IRestaurantRepository) : CommandLineRunner{

    override fun run(vararg args: String?){
        val csvReader = CsvReader(cuisineRepository);
        val cuisines = csvReader.readCuisiceCsv();
        cuisineRepository.saveAll(cuisines);

        val restaurants = csvReader.readRestaurantCsv();
        restaurantRepository.saveAll(restaurants);
    }


}