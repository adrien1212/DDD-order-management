package fr.adriencaubel.ordermanagement.service;

import fr.adriencaubel.ordermanagement.domain.*;
import fr.adriencaubel.ordermanagement.model.OrderItemRequestModel;
import fr.adriencaubel.ordermanagement.model.OrderRequestModel;
import fr.adriencaubel.ordermanagement.repository.CustomerRepository;
import fr.adriencaubel.ordermanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final InventoryService inventoryService;
    private final ArticleService articleService;
    private final OrderRepository orderRepository;

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public Order placeOrder(OrderRequestModel orderRequestModel) {
        Customer customer = customerRepository.findById(orderRequestModel.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Create the order
        Order order = new Order();
        order.setCustomer(customer);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequestModel dto : orderRequestModel.getItems()) {
            Inventory inventory = inventoryService.getInventoryByArticleId(dto.getArticleId());
            if (inventory.getStock() < dto.getQuantity()) {
                throw new IllegalStateException("Item out of stock");
            } else {
                inventory.setStock(inventory.getStock() - dto.getQuantity());
                inventory.setLastUpdate(LocalDate.now());
            }

            Article article = articleService.getArticle(dto.getArticleId());
            BigDecimal price = article.getPrice();
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(dto.getQuantity()));
            if (customer.isVip()) {
                lineTotal = lineTotal.multiply(new BigDecimal("0.95")); // 5% discount for VIPs
            }

            // Add the item to the order
            OrderItem orderItem = new OrderItem();
            orderItem.setArticle(article);
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setPrice(price);
            orderItem.setLineTotal(lineTotal);
            orderItem.setOrder(order);

            order.getItems().add(orderItem);

            total = total.add(lineTotal);
        }

        // Set the total of the order
        order.setTotal(total);

        return orderRepository.save(order);
    }
}
