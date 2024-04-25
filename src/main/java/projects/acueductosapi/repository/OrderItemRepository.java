package projects.acueductosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.acueductosapi.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}