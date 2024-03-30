package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.Product;

public interface ProductoRepository extends JpaRepository<Product, Integer> {
}