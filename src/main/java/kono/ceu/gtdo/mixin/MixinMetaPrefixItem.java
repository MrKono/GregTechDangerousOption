package kono.ceu.gtdo.mixin;

import static kono.ceu.gtdo.api.util.GTDOValues.explodeMaterialMap;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

@Mixin(value = MetaPrefixItem.class, remap = false)
public abstract class MixinMetaPrefixItem {

    @Final
    @Shadow
    private OrePrefix prefix;

    @Unique
    private final boolean explode = true;
    @Unique
    private static final Map<OrePrefix, Float> explodePrefixMap = new HashMap<>();

    static {
        explodePrefixMap.put(OrePrefix.ingot, 1.0F);
        explodePrefixMap.put(OrePrefix.dust, 1.0F);
        explodePrefixMap.put(OrePrefix.dustSmall, 0.25F);
        explodePrefixMap.put(OrePrefix.nugget, 0.1F);
        explodePrefixMap.put(OrePrefix.dustTiny, 0.1F);
    }

    @Inject(method = "onEntityItemUpdate",
            at = @At(value = "INVOKE",
                     target = "Lgregtech/api/items/materialitem/MetaPrefixItem;getMaterial(Lnet/minecraft/item/ItemStack;)Lgregtech/api/unification/material/Material;"))
    public void onEntityItemUpdateMixin(EntityItem itemEntity, CallbackInfoReturnable<Boolean> cir) {
        if (explode) {
            MetaPrefixItem metaPrefixItem = (MetaPrefixItem) (Object) this;
            int count = itemEntity.getItem().getCount();
            ItemStack stack = itemEntity.getItem();
            World worlds = itemEntity.getEntityWorld();
            Material mat = metaPrefixItem.getMaterial(itemEntity.getItem());
            if (explodePrefixMap.containsKey(this.prefix)) {
                if (explodeMaterialMap.containsKey(mat)) {
                    float strength = explodePrefixMap.get(this.prefix) * explodeMaterialMap.get(mat) *
                            (1 + ((float) (count - 1) / 100));
                    if (itemEntity.isWet()) {
                        itemEntity.world.createExplosion(itemEntity, itemEntity.posX, itemEntity.posY, itemEntity.posZ,
                                strength, true);
                        itemEntity.setDead();
                    }
                    BlockPos blockPos = new BlockPos(itemEntity);
                    IBlockState blockState = itemEntity.getEntityWorld().getBlockState(blockPos);
                    if ((blockState.getBlock() instanceof BlockCauldron)) {
                        int waterLevel = blockState.getValue(BlockCauldron.LEVEL);
                        if (waterLevel != 0) {
                            itemEntity.getEntityWorld().setBlockState(blockPos,
                                    blockState.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
                            itemEntity.world.createExplosion(itemEntity, itemEntity.posX, itemEntity.posY,
                                    itemEntity.posZ, strength, true);
                            itemEntity.setDead();
                        }
                    }
                }
            }

        }
    }
}
