package top.ycao.ynftweak.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class GenPlayerCmdCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("genplayercmd").executes(
                GenPlayerCmdCommand::exec
        ));
    }

    private static int exec(CommandContext<ServerCommandSource> context) {
        ServerCommandSource src = null;
        ServerPlayerEntity player = null;
        try {
            src = context.getSource();
            player = src.getPlayer();
        } catch (CommandSyntaxException e) {
            src.sendError(Text.of("You must be a player to use this command!"));
            return 1;
        }
        Vec3d pos = player.getPos();
        float pitch = player.getPitch();
        float yaw = player.getYaw();
        String worldRegKeyStr = player.getEntityWorld().getRegistryKey().getValue().toString();
        String reply = String.format("/player p spawn at %.2f %.2f %.2f facing %.2f %.2f in %s",
                pos.x, pos.y, pos.z, pitch, yaw, worldRegKeyStr);
        Text clickableText = new LiteralText(reply)
                .styled(style -> style.withHoverEvent(
                        new net.minecraft.text.HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT,
                                new LiteralText("Click to copy"))
                ))
                .styled(style -> style.withClickEvent(
                new net.minecraft.text.ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, reply)
                ))
                .styled(style -> style.withColor(net.minecraft.util.Formatting.GREEN));
        player.sendMessage(clickableText, false);
        return 0;
    }
}
