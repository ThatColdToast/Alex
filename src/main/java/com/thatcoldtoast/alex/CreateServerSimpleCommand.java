package com.thatcoldtoast.alex;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateServerSimpleCommand implements SimpleCommand {
    private final ProxyServer proxy;
    private final Logger logger;

    public CreateServerSimpleCommand(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        String prefix = "survival-";

        var indicies = proxy.getAllServers().stream().map(s -> s.getServerInfo().getName()).filter(s -> s.startsWith(prefix))
                .map(s -> Integer.parseInt(s.substring(prefix.length()))).toList();

        int index = 1;
        for (int i = 0; i < indicies.size(); i++) {
            if (!indicies.contains(i)) {
                index = i;
                break;
            }
        }

        source.sendMessage(Component.text("survival-" + index).color(NamedTextColor.AQUA));

        proxy.registerServer(new ServerInfo("survival-" + index, new InetSocketAddress("127.0.0.1", 25565)));

//        proxy.registerServer(new ServerInfo("survival-1", new InetSocketAddress("survival-1.survival.default.svc.cluster.local", 25565)));
//        proxy.registerServer(new ServerInfo("survival-2", new InetSocketAddress("survival-2.survival.default.svc.cluster.local", 25565)));

//        proxy.createRawRegisteredServer(new ServerInfo("survival-2", new InetSocketAddress("survival-2.survival.default.svc.cluster.local", 25565)));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return List.of();
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.completedFuture(List.of());
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
//        return invocation.source().hasPermission("createserver");
    }
}
