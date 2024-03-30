package projects.acueductosapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.acueductosapi.entities.Order;
import projects.acueductosapi.response.OrderResponseRest;
import projects.acueductosapi.services.OrderService;


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
}
