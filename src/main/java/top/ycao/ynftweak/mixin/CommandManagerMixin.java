package top.ycao.ynftweak.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ycao.ynftweak.command.GenPlayerCmdCommand;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Shadow @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;
    
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"),
            method = "<init>")
    private void registerCustomCommands(CommandManager.RegistrationEnvironment environment, CallbackInfo ci) {
        GenPlayerCmdCommand.register(dispatcher);
    }

}
