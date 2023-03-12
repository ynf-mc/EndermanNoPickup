package top.ycao.ynftweak.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin {
    @Shadow
    private boolean editable;

    @Shadow
    private UUID editor;

    @Shadow
    public abstract void setEditable(boolean editable);

    @Inject(method = "onActivate", at = @At("HEAD"))
    public void editSign(ServerPlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
        if (player.canModifyBlocks() && player.getMainHandStack().getItem() == Items.AIR && player.isSneaking()) {
            this.setEditable(true);
            player.openEditSignScreen((SignBlockEntity) (Object) this);
        }
    }

    @Final
    @Shadow
    private Text[] texts;

    @Inject(method = "onActivate", at = @At("HEAD"))
    public void runCommandOnActivated(ServerPlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
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
            commandSource.getServer().getCommandManager().execute(commandSource, actualCommand);
        }
    }

}