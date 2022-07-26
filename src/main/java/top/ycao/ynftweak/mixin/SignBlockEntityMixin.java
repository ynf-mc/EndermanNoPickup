package top.ycao.ynftweak.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.ycao.ynftweak.YnFInitializer;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin {
    @Shadow
    abstract public void setEditable(boolean editable);

    @Inject(method = "onActivate", at = @At("HEAD"))
    public void editSign(PlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
        if (player.canModifyBlocks() && player.getMainHandStack().getItem() == Items.AIR && player.isSneaking()) {
            this.setEditable(true);
            player.openEditSignScreen((SignBlockEntity) (Object) this);
        }
    }

    @Shadow
    protected Text[] texts;

    @Inject(method = "onActivate", at = @At("HEAD"))
    public void runCommandOnActivated(PlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
        Text[] texts = this.texts;
        StringBuilder rawText = new StringBuilder();
        for (Text t : texts) {
            rawText.append(t.getString());
        }
        String text = rawText.toString();
        if (text.startsWith("/") && player.getMainHandStack().getItem() == Items.AIR && !player.isSneaking()) {
            String actualCommand = text.substring(1);
            // No cheating!
            ServerCommandSource commandSource = player.getCommandSource();
            commandSource.getMinecraftServer().getCommandManager().execute(commandSource, actualCommand);
        }
    }

}