package com.example.bestmatchedrestaurants.controller

import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import com.example.bestmatchedrestaurants.service.RestaurantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurants/")
class RestaurantController (val service: RestaurantService){
    @GetMapping("/")
    fun findAll() {
        service.readRestaurantCsv();
        service.findAll();
    }

    @GetMapping("/filter")
    fun findFiltered(@RequestBody restaurant: RestaurantFilter) {
        service.findByFilter(restaurant);
    }

}