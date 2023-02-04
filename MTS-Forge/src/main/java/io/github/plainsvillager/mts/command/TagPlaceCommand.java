package io.github.plainsvillager.mts.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraftforge.forgespi.locating.ForgeFeature;

import java.util.Random;
import java.util.logging.Level;

public class TagPlaceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("mts:tag_place").requires((a)->{
            return a.hasPermission(3);
        }).then(Commands.literal("airport").executes((b)->{
            return
        })).then(Commands.literal("test").executes((c)->{

        })))
    }
    public static final int getRandom(CommandSourceStack css){
        var level = css.getLevel();
        int randomValue = new Random().nextInt();
        css.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(randomValue + "")), new ForgeFeature.Bound());
        return 1;
    }
}
