package fr.adriencaubel.ordermanagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    public void addItem(Article article, int quantity, boolean isVip) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        BigDecimal lineTotal = article.getPrice().multiply(BigDecimal.valueOf(quantity));
        if(isVip) {
            lineTotal = lineTotal.multiply(new BigDecimal("0.95"));
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setArticle(article);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(article.getPrice());
        orderItem.setLineTotal(lineTotal);
        orderItem.setOrder(this);
        items.add(orderItem);

        total = total.add(lineTotal);
    }
}
