package com.example.bestmatchedrestaurants.controller

import com.example.bestmatchedrestaurants.model.CuisineModel
import com.example.bestmatchedrestaurants.service.CuisineService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cuisines/")
abstract class CuisineController(val service: CuisineService) : List<CuisineModel>{
    fun findAll(): Iterable<CuisineModel> = service.findAll();
}