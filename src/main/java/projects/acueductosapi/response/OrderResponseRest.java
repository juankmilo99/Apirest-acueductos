package projects.acueductosapi.response;

public class OrderResponseRest extends ResponseRest{
    private OrderResponse orderResponse = new OrderResponse();

    public OrderResponse getOrderResponse() {
        return orderResponse;
    }

    public void setOrderResponse(OrderResponse orderResponse) {
        this.orderResponse = orderResponse;
    }
}
