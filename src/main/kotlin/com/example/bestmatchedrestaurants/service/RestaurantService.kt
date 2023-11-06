package com.example.bestmatchedrestaurants.service

import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import jakarta.persistence.criteria.Predicate
import org.springframework.stereotype.Service


@Service
class RestaurantService(val repository: IRestaurantRepository, @PersistenceContext val entityManager: EntityManager) {
    fun findAll() = repository.findAll();
    fun findByFilter(restaurant: RestaurantFilter) : List<RestaurantModel> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder;
        val criteriaQuery: CriteriaQuery<RestaurantModel> = builder.createQuery(RestaurantModel::class.java);
        val entity: Root<RestaurantModel> = criteriaQuery.from(RestaurantModel::class.java);

        var predicates: MutableList<Predicate> = createPredicates(builder, entity, restaurant);

        criteriaQuery.where(*predicates.toTypedArray());

        return entityManager.createQuery(criteriaQuery).setMaxResults(5).resultList;
    }

    fun createPredicates(queryBuilder: CriteriaBuilder, entity: Root<RestaurantModel>, restaurant: RestaurantFilter): MutableList<Predicate> {
        val predicates = mutableListOf<Predicate>();

        restaurant.name?.let {
            predicates.add(queryBuilder.like(queryBuilder.lower(entity.get<String>("name")), "%$it%"));
        }

        return predicates;
    }
}