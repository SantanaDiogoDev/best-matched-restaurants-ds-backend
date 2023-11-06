package com.example.bestmatchedrestaurants.repository

import com.example.bestmatchedrestaurants.model.CuisineModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ICuisineRepository : JpaRepository<CuisineModel, Int>{
}