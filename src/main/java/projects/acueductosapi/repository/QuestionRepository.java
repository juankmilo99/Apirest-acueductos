package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projects.acueductosapi.entities.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT q FROM Question q WHERE q.product_id = :productId")
    List<Question> findByProductId(@Param("productId") Integer productId);
}