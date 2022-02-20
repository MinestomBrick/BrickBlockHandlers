package org.minestombrick.blockhandlers.app.listeners;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.utils.NamespaceID;
import org.minestombrick.blockhandlers.api.events.ChestOpenEvent;
import org.minestombrick.blockhandlers.api.inventory.BlockInventory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChestListener {

    private final static Set<NamespaceID> blockTypes = Set.of(
            Block.CHEST.namespace(),
            Block.TRAPPED_CHEST.namespace()
    );

    private final Map<Point, BlockInventory> inventories = new ConcurrentHashMap<>();

    public ChestListener() {
        GlobalEventHandler geh = MinecraftServer.getGlobalEventHandler();
        geh.addListener(PlayerBlockInteractEvent.class, this::onInteract);
        geh.addListener(InventoryCloseEvent.class, this::onClose);
    }

    private void onClose(InventoryCloseEvent event) {
        if ( !(event.getInventory() instanceof BlockInventory inv) ) {
            return;
        }

        if ( inv.getViewers().size() > 1 ) {
            return;
        }

        inventories.remove(inv.blockPosition());

        // TODO only players in visible range
        // chest close animation
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(p ->
                p.sendPacket(new BlockActionPacket(inv.blockPosition(), (byte) 1, (byte) 0, Block.CHEST.id())));
    }

    private void onInteract(PlayerBlockInteractEvent event) {
        Block block = event.getBlock();
        if ( !blockTypes.contains(block.namespace()) ) {
            return;
        }

        Player player = event.getPlayer();
        if ( player.isSneaking() ) {
            return;
        }

        Point blockPos = event.getBlockPosition();

        BlockInventory inventory;
        if ( inventories.containsKey(blockPos) ) {
            inventory = inventories.get(blockPos);
        } else {
            // TODO load from persistent data storage
            inventory = new BlockInventory(InventoryType.CHEST_3_ROW, "Chest", blockPos);
            inventories.put(blockPos, inventory);
        }

        ChestOpenEvent chestEvent = new ChestOpenEvent(event.getPlayer(), blockPos, block, inventory);
        MinecraftServer.getGlobalEventHandler().call(chestEvent);

        event.setCancelled(true);
        if ( chestEvent.isCancelled() ) {
            return;
        }

        if ( chestEvent.inventory() instanceof BlockInventory ) {
            player.openInventory(chestEvent.inventory());
        }

        // TODO only players in visible range
        // chest open animation
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(p ->
                p.sendPacket(new BlockActionPacket(blockPos, (byte) 1, (byte) 1, block.id())));
    }

}
