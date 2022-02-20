package org.minestombrick.blockhandlers.api.inventory;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Point;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class BlockInventory extends Inventory {

    private final Point blockPosition;

    public BlockInventory(@NotNull InventoryType inventoryType, @NotNull Component title, Point blockPosition) {
        super(inventoryType, title);
        this.blockPosition = blockPosition;
    }

    public BlockInventory(@NotNull InventoryType inventoryType, @NotNull String title, Point blockPosition) {
        super(inventoryType, title);
        this.blockPosition = blockPosition;
    }

    public final Point blockPosition() {
        return blockPosition;
    }

}
