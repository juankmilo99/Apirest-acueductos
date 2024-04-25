package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projects.acueductosapi.entities.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.product_id = :productId")
    List<Review> findByProductId(@Param("productId") Integer productId);
}