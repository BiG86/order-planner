package it.snorcini.dev.orderplanner.repository;

import it.snorcini.dev.orderplanner.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA interface to manage Book Entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByIsbn(@Param("isbn") String isbn);

    /**
     * Find all books
     *
     * @param id       book id to be searched
     * @param pageable whether the query is paged or not
     * @return the books page
     */
    @Query("SELECT a FROM Book a"
            + " WHERE (:id IS NULL OR a.id = :id)")
    Page<Order> findAll(@Param("id") Long id,
                        Pageable pageable);
}
