package top.ycao.ynftweak.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.mob.EndermanEntity$PickUpBlockGoal"})
abstract public class EndermanEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/tag/Tag;)Z"))
    private boolean isInExtraCheck(BlockState block, Tag<Block> tag) {
        return block.isIn(BlockTags.ENDERMAN_HOLDABLE) &&
                (!block.isOf(Blocks.BROWN_MUSHROOM)) &&
                (!block.isOf(Blocks.RED_MUSHROOM));
    }
}
