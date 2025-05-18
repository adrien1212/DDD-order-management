package fr.adriencaubel.ordermanagement.model;

import fr.adriencaubel.ordermanagement.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseModel {
    private Long id;
    private Long articleId;
    private int quantity;

    public static OrderItemResponseModel toResponseModel(OrderItem orderItem) {
        OrderItemResponseModel response = new OrderItemResponseModel();
        response.setId(orderItem.getId());
        response.setArticleId(orderItem.getArticle().getId());
        response.setQuantity(orderItem.getQuantity());
        return response;
    }
}
