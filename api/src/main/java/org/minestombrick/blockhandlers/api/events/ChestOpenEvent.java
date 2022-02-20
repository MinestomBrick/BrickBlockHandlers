package org.minestombrick.blockhandlers.api.events;

import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.Inventory;

public class ChestOpenEvent implements CancellableEvent {

    private final Player player;
    private final Point blockPosition;
    private final Block block;

    private boolean cancelled;
    private Inventory inventory;

    public ChestOpenEvent(Player player, Point blockPosition, Block block, Inventory inventory) {
        this.player = player;
        this.blockPosition = blockPosition;
        this.block = block;
        this.inventory = inventory;
    }

    public Player player() {
        return player;
    }

    public Block block() {
        return block;
    }

    public Point blockPosition() {
        return blockPosition;
    }

    public Inventory inventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
