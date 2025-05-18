package fr.adriencaube.ordermanagement;

import fr.adriencaubel.ordermanagement.domain.Article;
import fr.adriencaubel.ordermanagement.domain.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    void addItem_shouldCalculateTotal() {
        Article article = new Article();
        article.setPrice(new BigDecimal("100"));

        Order order = new Order();

        order.addItem(article, 2, false); // Client VIP

        BigDecimal expectedLineTotal = new BigDecimal("100").multiply(BigDecimal.valueOf(2));
        assertEquals(expectedLineTotal, order.getTotal());
        assertEquals(1, order.getItems().size());
        assertEquals(expectedLineTotal, order.getItems().get(0).getLineTotal());
    }

    @Test
    void addItem_shouldCalculateTotalWithVipDiscount() {
        Article article = new Article();
        article.setPrice(new BigDecimal("100"));

        Order order = new Order();

        order.addItem(article, 2, true); // Client VIP

        BigDecimal expectedLineTotal = new BigDecimal("100").multiply(BigDecimal.valueOf(2)).multiply(new BigDecimal("0.95"));
        assertEquals(expectedLineTotal, order.getTotal());
        assertEquals(1, order.getItems().size());
        assertEquals(expectedLineTotal, order.getItems().get(0).getLineTotal());
    }
}
