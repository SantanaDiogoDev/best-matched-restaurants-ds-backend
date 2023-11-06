package com.example.bestmatchedrestaurants.service

import com.example.bestmatchedrestaurants.model.CuisineModel
import com.example.bestmatchedrestaurants.repository.ICuisineRepository
import org.springframework.stereotype.Service

@Service
class CuisineService(val repository: ICuisineRepository) {
    fun findAll(): Iterable<CuisineModel> = repository.findAll();
}