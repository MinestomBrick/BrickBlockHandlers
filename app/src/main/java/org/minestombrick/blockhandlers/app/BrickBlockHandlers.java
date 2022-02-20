package org.minestombrick.blockhandlers.app;

import net.minestom.server.extensions.Extension;
import org.minestombrick.blockhandlers.app.listeners.ChestListener;
import org.minestombrick.blockhandlers.app.listeners.DoorListener;

public class BrickBlockHandlers extends Extension {

    @Override
    public void initialize() {
        getLogger().info("Enabling " + nameAndVersion() + ".");

        new ChestListener();
        new DoorListener();

        getLogger().info("Enabled " + nameAndVersion() + ".");
    }

    @Override
    public void terminate() {
        getLogger().info("Disabled " + nameAndVersion() + ".");
    }

    private String nameAndVersion() {
        return getOrigin().getName() + " v" + getOrigin().getVersion();
    }
}