
package fr.adriencaubel.ordermanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestModel {
    private Long articleId;
    private int quantity;
}
