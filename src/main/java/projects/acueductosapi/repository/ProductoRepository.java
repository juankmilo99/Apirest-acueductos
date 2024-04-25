package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projects.acueductosapi.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.id_category = :id_category")
    List<Product> findByIdCategory(@Param("id_category") Integer id_category);



}