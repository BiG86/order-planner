package it.snorcini.dev.orderplanner.repository;

import it.snorcini.dev.orderplanner.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String>, QuerydslPredicateExecutor<Order> {


    Optional<Order> findByUid(String uid);
}
