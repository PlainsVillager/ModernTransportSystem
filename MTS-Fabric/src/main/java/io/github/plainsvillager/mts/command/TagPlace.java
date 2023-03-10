package io.github.plainsvillager.mts.command;


import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import java.util.Random;

import static net.minecraft.server.command.CommandManager.*;

public class TagPlace {
    public static void registerCommand(){
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher.register(literal("mts:tag_place")
                                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(3))
                .executes(context -> {
                    context.getSource().sendMessage(Text.literal("随机数：" + new Random().nextInt()));
                    return 1;
                }))
        );
    }
}
