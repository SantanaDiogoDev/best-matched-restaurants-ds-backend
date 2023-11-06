package com.example.bestmatchedrestaurants.service

import com.example.bestmatchedrestaurants.model.CuisineModel
import com.example.bestmatchedrestaurants.repository.ICuisineRepository
import org.springframework.stereotype.Service

@Service
class CuisineService(val repository: ICuisineRepository) {
    fun findAll(): Iterable<CuisineModel> = repository.findAll();

    fun readCuisiceCsv(){
        val cuisineList = mutableListOf<CuisineModel>();
        val cuisineFile = this::class.java.getResource("/static/cuisines.csv").readText();
        val lines = cuisineFile.lines();
        for(i in 1 until lines.size){
            val values = lines[i].split(",");
            val id = values[0].toInt();
            val name = values[1];
            cuisineList.add(CuisineModel(id, name));
        }
    }
}