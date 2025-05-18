package fr.adriencaubel.ordermanagement.controller;

import fr.adriencaubel.ordermanagement.model.OrderRequestModel;
import fr.adriencaubel.ordermanagement.model.OrderResponseModel;
import fr.adriencaubel.ordermanagement.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseModel> getOrder(@PathVariable Long id) {
        OrderResponseModel orderResponseModel = OrderResponseModel.toResponseModel(orderService.getOrder(id));
        return ResponseEntity.ok(orderResponseModel);
    }

    @PostMapping
    public ResponseEntity<OrderResponseModel> placeOrder(@RequestBody OrderRequestModel order) {
        OrderResponseModel responseModel = OrderResponseModel.toResponseModel(orderService.placeOrder(order));
        return ResponseEntity.ok(responseModel);
    }
}
