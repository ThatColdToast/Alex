package com.thatcoldtoast.alex;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "alex", name = "Alex server control system", version = "0.1.0-SNAPSHOT",
        url = "https://example.org", description = "I did it!", authors = {"ThatColdToast"})
public class Alex {
    private final ProxyServer proxy;
    private final Logger logger;

    @Inject
    public Alex(ProxyServer server, Logger logger) {
        this.proxy = server;
        this.logger = logger;

        logger.info("Alex Construction");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Alex Initialization");

//        server.getEventManager().register(this, new CreateServerCommand());

        CommandManager commandManager = proxy.getCommandManager();

        CommandMeta createServerCommandMeta = commandManager.metaBuilder("createserver")
                // This will create a new alias for the command "/test"
                // with the same arguments and functionality
                .aliases("cs")
                .plugin(this)
                .build();

        SimpleCommand createServerCommand = new CreateServerSimpleCommand(proxy, logger);

//        BrigadierCommand createServerCommand = AddServerBrigadierCommand.createBrigadierCommand(proxy);
//        CommandMeta createServerCommandMeta = commandManager.metaBuilder(createServerCommand).build();

        commandManager.register(createServerCommandMeta, createServerCommand);
    }
}