package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Order;
import projects.acueductosapi.response.OrderResponseRest;

public interface OrderService {

    public ResponseEntity<OrderResponseRest> buscarOrders();


    public ResponseEntity<OrderResponseRest> crear(Order order);
}
