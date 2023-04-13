package com.thatcoldtoast.alex;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.net.InetSocketAddress;
import java.util.List;

public class AddServerBrigadierCommand {
    public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> helloNode = LiteralArgumentBuilder
                .<CommandSource>literal("ac")
                // Here you can filter the subjects that can execute the command.
                // This is the ideal place to do "hasPermission" checks
//                .requires(source -> source.hasPermission("alex.createserver"))
                // Here you can add the logic that will be used in
                // the execution of the "/test" command without any argument
                .executes(context -> {
                    // Here you get the subject that executed the command
                    CommandSource source = context.getSource();

                    Component message = Component.text("You must provide a server type\nFormat:\n/createserver <servertype>\nExample:\n/createserver survival", NamedTextColor.RED);
                    source.sendMessage(message);

                    // Returning Command.SINGLE_SUCCESS means that the execution was successful
                    // Returning BrigadierCommand.FORWARD will send the command to the server
                    return Command.SINGLE_SUCCESS;
                })

                // Using the "then" method, you can add subarguments to the command.
                // For example, this subcommand will be executed when using the command "/test <some argument>"
                // A RequiredArgumentBuilder is a type of argument in which you can enter some undefined data
                // of some kind. For example, this example uses a StringArgumentType.word() that requires
                // a single word to be entered, but you can also use different ArgumentTypes provided by Brigadier
                // that return data of type Boolean, Integer, Float, other String types, etc
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("servertype", StringArgumentType.word())
                        // Here you can define the hints to be provided in case the ArgumentType does not provide them.
                        // In this example, the names of all connected players are provided
                        .suggests((ctx, builder) ->
                                        builder.suggest("survival").buildFuture()
//                                    proxy.getAllPlayers().forEach(player -> builder.suggest(
//                                            player.getUsername()
//                                    ).buildFuture());
//                                }

                                // Here we provide the names of the players along with a tooltip,
                                // which can be used as an explanation of a specific argument or as a simple decoration
//                                proxy.getAllPlayers().forEach(player -> builder.suggest(
//                                        player.getUsername(),
//                                        // A VelocityBrigadierMessage takes a component.
//                                        // In this case, the player's name is provided with a rainbow
//                                        // gradient created by MiniMessage (Library available since Velocity 3.1.2+)
//                                        VelocityBrigadierMessage.tooltip(
//                                                MiniMessage.miniMessage().deserialize("<rainbow>" + player.getUsername())
//                                        )
//                                ))
                        )
                        .executes(context -> {
                            String serverType = context.getArgument("servertype", String.class);
                            List<String> serverNames = proxy.getAllServers().stream()
                                    .map(x -> x.getServerInfo().getName())
                                    .filter(x -> x.startsWith(serverType + "-")).toList();

                            int availableIndex = 1;
                            for(int i = 0; i < serverNames.size(); i++){
                                String name = serverNames.get(i);

                                if (!name.endsWith("-" + (i + 1))) {
                                    availableIndex = i + 1;
                                    break;
                                }
                            }

//                            ApiClient client = Config.defaultClient();
//                            Configuration.setDefaultApiClient(client);
//
//                            CoreV1Api api = new CoreV1Api();
//                            V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
//                            for (V1Pod item : list.getItems()) {
//                                System.out.println(item.getMetadata().getName());
//                            }

                            proxy.registerServer(new ServerInfo(serverType + "-" + availableIndex, new InetSocketAddress(serverType + "-" + availableIndex + "." + serverType + ".default.svc.cluster.local", 25565)));

                            Component message = Component.text("Creating server '" + serverType + "-" + availableIndex + "'", NamedTextColor.GREEN);
                            context.getSource().sendMessage(message);

                            // Returning Command.SINGLE_SUCCESS means that the execution was successful
                            // Returning BrigadierCommand.FORWARD will send the command to the server
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();

        // BrigadierCommand implements Command
        return new BrigadierCommand(helloNode);
    }
}
