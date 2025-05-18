package fr.adriencaubel.ordermanagement.model;

import fr.adriencaubel.ordermanagement.domain.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseModel {
    private Long id;
    private Long customerId;
    private Double total;
    private List<OrderItemResponseModel> items;

    public static OrderResponseModel toResponseModel(Order order) {
        OrderResponseModel response = new OrderResponseModel();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setTotal(order.getTotal().doubleValue());
        response.setItems(order.getItems().stream().map(OrderItemResponseModel::toResponseModel).toList());
        return response;
    }
}
