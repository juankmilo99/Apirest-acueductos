package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}