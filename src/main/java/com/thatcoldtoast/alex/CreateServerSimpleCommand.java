package com.thatcoldtoast.alex;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateServerSimpleCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        source.sendMessage(Component.text("Hello World!").color(NamedTextColor.AQUA));

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
