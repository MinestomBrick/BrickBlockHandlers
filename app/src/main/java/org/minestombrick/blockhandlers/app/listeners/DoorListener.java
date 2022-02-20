package org.minestombrick.blockhandlers.app.listeners;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;

import java.util.Set;

public class DoorListener {

    private final static Set<NamespaceID> blockTypes = Set.of(
            Block.OAK_DOOR.namespace(),
            Block.DARK_OAK_DOOR.namespace()
            // TODO
    );

    public DoorListener() {
        GlobalEventHandler geh = MinecraftServer.getGlobalEventHandler();
        geh.addListener(PlayerBlockInteractEvent.class, this::onInteract);
        geh.addListener(PlayerBlockPlaceEvent.class, this::onPlace);
        geh.addListener(PlayerBlockBreakEvent.class, this::onBreak);
    }

    private void onInteract(PlayerBlockInteractEvent event) {
        Block block = event.getBlock();
        if ( !blockTypes.contains(block.namespace()) ) {
            return;
        }

        Player player = event.getPlayer();
        if ( player.isSneaking() ) {
            event.setCancelled(true);
            return;
        }

        Point blockPos = event.getBlockPosition();

        // TODO
        System.out.println("interacted with door");
    }

    private void onPlace(PlayerBlockPlaceEvent event) {
        Block block = event.getBlock();
        if ( !blockTypes.contains(block.namespace()) ) {
            return;
        }

        Player player = event.getPlayer();
        Point blockPos = event.getBlockPosition();

        // TODO
        System.out.println("placed door");
    }

    private void onBreak(PlayerBlockBreakEvent event) {
        Block block = event.getBlock();
        if ( !blockTypes.contains(block.namespace()) ) {
            return;
        }

        Player player = event.getPlayer();
        Point blockPos = event.getBlockPosition();

        // TODO
        System.out.println("broken door");
    }
}
