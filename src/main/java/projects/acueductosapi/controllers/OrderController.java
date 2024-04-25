package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.Order;
import projects.acueductosapi.response.OrderResponseRest;
import projects.acueductosapi.services.OrderService;

import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @GetMapping("")
    public ResponseEntity<OrderResponseRest> consultarOrders() {
        ResponseEntity<OrderResponseRest> response = orderService.buscarOrders();
        return response;
    }

    @PostMapping("")
    public ResponseEntity<OrderResponseRest> crearOrder(@RequestBody Order order) {
        ResponseEntity<OrderResponseRest> response = orderService.crear(order);
        return response;
    }

    @PostMapping("/cart/{orderId}/{productId}/{quantity}")
    public ResponseEntity<OrderResponseRest> addToCart(@PathVariable Integer orderId, @PathVariable Integer productId, @PathVariable Integer quantity) {
        ResponseEntity<OrderResponseRest> response = orderService.addToCart(orderId, productId, quantity);
        return response;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseRest> buscarOrderPorId(@PathVariable Integer orderId) {
        ResponseEntity<OrderResponseRest> response = orderService.buscarOrderPorId(orderId);
        return response;
    }

    @PutMapping("/increase/{orderId}/{productId}/{quantity}")
    public ResponseEntity<OrderResponseRest> increaseItemQuantity(@PathVariable Integer orderId, @PathVariable Integer productId, @PathVariable Integer quantity) {
        ResponseEntity<OrderResponseRest> response = orderService.increaseItemQuantity(orderId, productId, quantity);
        return response;
    }

    @PutMapping("/decrease/{orderId}/{productId}/{quantity}")
    public ResponseEntity<OrderResponseRest> decreaseItemQuantity(@PathVariable Integer orderId, @PathVariable Integer productId, @PathVariable Integer quantity) {
        ResponseEntity<OrderResponseRest> response = orderService.decreaseItemQuantity(orderId, productId, quantity);
        return response;
    }
    @PutMapping("/update/{orderId}/{productId}/{quantity}")
    public ResponseEntity<OrderResponseRest> updateItemQuantity(@PathVariable Integer orderId, @PathVariable Integer productId, @PathVariable Integer quantity) {
        ResponseEntity<OrderResponseRest> response = orderService.updateItemQuantity(orderId, productId, quantity);
        return response;
    }

    @PostMapping("/send-email")
    public ResponseEntity<OrderResponseRest> sendEmail(@RequestBody Map<String, String> emailData) {
        return orderService.sendEmail(emailData);
    }
}
