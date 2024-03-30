package projects.acueductosapi.response;

import projects.acueductosapi.entities.Order;
import projects.acueductosapi.entities.Product;

import java.util.List;

public class OrderResponse {
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
