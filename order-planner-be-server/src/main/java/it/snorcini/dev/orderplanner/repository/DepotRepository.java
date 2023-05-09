package it.snorcini.dev.orderplanner.repository;

import it.snorcini.dev.orderplanner.entity.Depot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Depot entity.
 */
@Repository
public interface DepotRepository extends MongoRepository<Depot, String>, QuerydslPredicateExecutor<Depot> {


    Optional<Depot> findByUid(String uid);
}
