package fr.adriencaube.ordermanagement;

import fr.adriencaubel.ordermanagement.domain.Inventory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryTest {

    @Test
    void decreaseStock_shouldDecreaseStockAndSetLastUpdate() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setStock(10);

        // Act
        inventory.decreaseStock(3);

        // Assert
        assertEquals(7, inventory.getStock());
        assertEquals(LocalDate.now(), inventory.getLastUpdate());
    }

    @Test
    void decreaseStock_shouldThrowIfQuantityIsZero() {
        Inventory inventory = new Inventory();
        inventory.setStock(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                inventory.decreaseStock(0)
        );
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void decreaseStock_shouldThrowIfQuantityIsNegative() {
        Inventory inventory = new Inventory();
        inventory.setStock(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                inventory.decreaseStock(-5)
        );
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void decreaseStock_shouldThrowIfNotEnoughStock() {
        Inventory inventory = new Inventory();
        inventory.setStock(2);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                inventory.decreaseStock(5)
        );
        assertEquals("Item out of stock", exception.getMessage());
    }
}
