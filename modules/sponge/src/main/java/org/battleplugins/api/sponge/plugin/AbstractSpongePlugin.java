package org.battleplugins.api.sponge.plugin;

import com.google.inject.Inject;

import org.battleplugins.api.Platform;
import org.battleplugins.api.command.Command;
import org.battleplugins.api.command.CommandExecutor;
import org.battleplugins.api.logger.Logger;
import org.battleplugins.api.plugin.Plugin;
import org.battleplugins.api.plugin.platform.PlatformPlugin;
import org.battleplugins.api.sponge.logger.SpongeLogger;
import org.battleplugins.api.sponge.SpongePlatform;
import org.battleplugins.api.sponge.command.SpongeCommandExecutor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedEvent;
import org.spongepowered.api.plugin.PluginManager;

import java.io.File;
import java.util.List;

public class AbstractSpongePlugin implements PlatformPlugin {

    @Inject
    private org.slf4j.Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

    @Inject
    private PluginManager pluginManager;

    private boolean enabled = false;

    private Plugin plugin;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Platform.setInstance(new SpongePlatform());
        this.plugin = Platform.getPluginManager().initializePlugin(this);
        Platform.getPluginManager().enablePlugin(this.plugin);
        enabled = true;
    }

    @Listener
    public void onServerStop(GameStoppedEvent event) {
        enabled = false;
        Platform.getPluginManager().disablePlugin(this.plugin);
    }


    public boolean isEnabled() {
        return enabled;
    }

    public File getDataFolder() {
        return configDir;
    }

    @Override
    public void registerMCCommand(Command command, CommandExecutor executor) {
        List<String> aliases = command.getAliases();
        aliases.add(command.getLabel());
        Sponge.getCommandManager().register(this, new SpongeCommandExecutor(executor, command), aliases);
    }

    @Override
    public Logger getMCLogger() {
        return new SpongeLogger(logger);
    }
}