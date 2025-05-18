
package fr.adriencaubel.ordermanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestModel {
    private Long customerId;
    private List<OrderItemRequestModel> items;
}
