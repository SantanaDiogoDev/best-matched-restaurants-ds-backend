package com.example.bestmatchedrestaurants.service

import com.example.bestmatchedrestaurants.model.CuisineModel
import com.example.bestmatchedrestaurants.model.RestaurantFilter
import com.example.bestmatchedrestaurants.model.RestaurantModel
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Order
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

        val orders = createOrders(builder, entity, restaurant);
        criteriaQuery.orderBy(orders);

        return entityManager.createQuery(criteriaQuery).setMaxResults(5).resultList;
    }

    fun createPredicates(queryBuilder: CriteriaBuilder, entity: Root<RestaurantModel>, restaurant: RestaurantFilter): MutableList<Predicate> {
        val predicates = mutableListOf<Predicate>();

        restaurant.name ?. let {
            predicates.add(queryBuilder.like(queryBuilder.lower(entity.get<String>("name")), "%${it.lowercase()}%"));
        }

        restaurant.customerRating ?. let {
            predicates.add(queryBuilder.greaterThanOrEqualTo(entity.get<Int>("customerRating"), it.toInt()));
        }

        restaurant.distance ?. let {
            predicates.add(queryBuilder.lessThanOrEqualTo(entity.get<Int>("distance"), it.toInt()));
        }

        restaurant.price ?. let {
            predicates.add(queryBuilder.lessThanOrEqualTo(entity.get<Int>("price"), it.toInt()));
        }

        restaurant.cuisine ?. let {
            predicates.add(queryBuilder.like(queryBuilder.lower(entity.get<CuisineModel>("cuisine").get<String>("name")), "%${it.lowercase()}%"))
        }

        return predicates;
    }

    fun createOrders(builder: CriteriaBuilder, entity: Root<RestaurantModel>, restaurant: RestaurantFilter): List<Order> {
        val orders = mutableListOf<Order>();

        restaurant.distance ?. let {
            orders.add(builder.asc(entity.get<Int>("distance")));
        }

        restaurant.customerRating ?. let {
            orders.add(builder.desc(entity.get<Int>("customerRating")))
        }

        restaurant.price ?. let {
            orders.add(builder.asc(entity.get<Int>("price")))
        }

        return orders;
    }
}