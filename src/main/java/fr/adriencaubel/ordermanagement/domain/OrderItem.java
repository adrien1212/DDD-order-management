package fr.adriencaubel.ordermanagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Article article;
    private int quantity;
    private BigDecimal price;
    private BigDecimal lineTotal;
}
