package fr.adriencaubel.ordermanagement.service;

import fr.adriencaubel.ordermanagement.domain.Article;
import fr.adriencaubel.ordermanagement.domain.Customer;
import fr.adriencaubel.ordermanagement.domain.Inventory;
import fr.adriencaubel.ordermanagement.domain.Order;
import fr.adriencaubel.ordermanagement.model.OrderItemRequestModel;
import fr.adriencaubel.ordermanagement.model.OrderRequestModel;
import fr.adriencaubel.ordermanagement.repository.CustomerRepository;
import fr.adriencaubel.ordermanagement.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    public Order placeOrder(OrderRequestModel orderRequestModel) {
        Customer customer = customerRepository.findById(orderRequestModel.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Create the order
        Order order = new Order();
        order.setCustomer(customer);

        for (OrderItemRequestModel dto : orderRequestModel.getItems()) {
            Inventory inventory = inventoryService.getInventoryByArticleId(dto.getArticleId());
            if (inventory.getStock() < dto.getQuantity()) {
                throw new IllegalStateException("Item out of stock");
            } else {
                inventory.setStock(inventory.getStock() - dto.getQuantity());
                inventory.setLastUpdate(LocalDate.now());
            }

            Article article = articleService.getArticle(dto.getArticleId());

            order.addItem(article, dto.getQuantity() , customer.isVip());
        }

        return orderRepository.save(order);
    }
}
