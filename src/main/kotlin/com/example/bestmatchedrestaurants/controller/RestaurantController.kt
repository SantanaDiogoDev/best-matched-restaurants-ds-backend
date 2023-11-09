package com.example.bestmatchedrestaurants.controller

import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import com.example.bestmatchedrestaurants.service.RestaurantService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurants")
class RestaurantController (val service: RestaurantService){
    @GetMapping
    fun findAll() = service.findAll();

    @GetMapping("/filter")
    fun findByFilter(
        @RequestParam name: String?,
        @RequestParam customerRating: Int?,
        @RequestParam distance: Int?,
        @RequestParam price: Int?,
        @RequestParam cuisine: String?,
        @RequestBody restaurant: RestaurantFilter?
    ): ResponseEntity<List<RestaurantModel>> {
        val filter = RestaurantFilter(name, customerRating, distance, price, cuisine);
        return ResponseEntity.ok(service.findByFilter(restaurant ?: filter));
    }

}