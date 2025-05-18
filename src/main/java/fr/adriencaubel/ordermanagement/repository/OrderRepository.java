
package fr.adriencaubel.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.adriencaubel.ordermanagement.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
