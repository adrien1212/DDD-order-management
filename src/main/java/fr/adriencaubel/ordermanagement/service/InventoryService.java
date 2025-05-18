
package fr.adriencaubel.ordermanagement.service;

import fr.adriencaubel.ordermanagement.domain.Inventory;
import fr.adriencaubel.ordermanagement.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public Inventory getInventoryByArticleId(Long articleId) {
        return inventoryRepository.findByArticleId(articleId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
