package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}