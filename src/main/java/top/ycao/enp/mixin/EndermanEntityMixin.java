package top.ycao.enp.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.mob.EndermanEntity$PickUpBlockGoal"})
abstract public class EndermanEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isIn(Lnet/minecraft/tag/Tag;)Z"))
    private boolean isInExtraCheck(Block block, Tag<Block> tag) {
        return block.isIn(BlockTags.ENDERMAN_HOLDABLE) &&
                (!block.is(Blocks.BROWN_MUSHROOM)) &&
                (!block.is(Blocks.RED_MUSHROOM));
    }
}
