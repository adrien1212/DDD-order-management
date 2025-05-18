
package fr.adriencaubel.ordermanagement.repository;

import fr.adriencaubel.ordermanagement.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
