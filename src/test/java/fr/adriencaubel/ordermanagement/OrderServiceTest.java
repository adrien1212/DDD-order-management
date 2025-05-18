package fr.adriencaubel.ordermanagement;

import fr.adriencaubel.ordermanagement.domain.Article;
import fr.adriencaubel.ordermanagement.domain.Customer;
import fr.adriencaubel.ordermanagement.domain.Inventory;
import fr.adriencaubel.ordermanagement.domain.Order;
import fr.adriencaubel.ordermanagement.model.OrderItemRequestModel;
import fr.adriencaubel.ordermanagement.model.OrderRequestModel;
import fr.adriencaubel.ordermanagement.repository.CustomerRepository;
import fr.adriencaubel.ordermanagement.repository.OrderRepository;
import fr.adriencaubel.ordermanagement.service.ArticleService;
import fr.adriencaubel.ordermanagement.service.InventoryService;
import fr.adriencaubel.ordermanagement.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private InventoryService inventoryService;
    @Mock
    private ArticleService articleService;
    @Mock private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder_shouldCalculateTotal() {
        // Arrange: préparer les mocks
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setVip(false);

        Article article = new Article();
        article.setId(10L);
        article.setPrice(new BigDecimal("100"));

        Inventory inventory = new Inventory();
        inventory.setArticle(article);
        inventory.setStock(10);

        OrderRequestModel request = new OrderRequestModel();
        request.setCustomerId(1L);

        OrderItemRequestModel item = new OrderItemRequestModel();
        item.setArticleId(10L);
        item.setQuantity(2);
        request.setItems(List.of(item));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(articleService.getArticle(10L)).thenReturn(article);
        when(inventoryService.getInventoryByArticleId(10L)).thenReturn(inventory);
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Order order = orderService.placeOrder(request);

        // Assert
        BigDecimal expectedLineTotal = new BigDecimal("100").multiply(new BigDecimal(2));
        assertEquals(expectedLineTotal, order.getTotal());
        assertEquals(1, order.getItems().size());
        assertEquals(expectedLineTotal, order.getItems().get(0).getLineTotal());
    }
}