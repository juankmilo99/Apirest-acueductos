package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Order;
import projects.acueductosapi.response.OrderResponseRest;

import java.util.Map;

public interface OrderService {

    public ResponseEntity<OrderResponseRest> buscarOrders();

    public ResponseEntity<OrderResponseRest> buscarOrderPorId(Integer orderId);

    public ResponseEntity<OrderResponseRest> crear(Order order);

    public ResponseEntity<OrderResponseRest> sendEmail(Map<String, String> emailData);

    public ResponseEntity<OrderResponseRest> addToCart(Integer orderId, Integer productId, Integer quantity);


    public ResponseEntity<OrderResponseRest> increaseItemQuantity(Integer orderId, Integer productId, Integer quantity);

    public ResponseEntity<OrderResponseRest> decreaseItemQuantity(Integer orderId, Integer productId, Integer quantity);


    public ResponseEntity<OrderResponseRest> updateItemQuantity(Integer orderId, Integer productId, Integer quantity);
}
