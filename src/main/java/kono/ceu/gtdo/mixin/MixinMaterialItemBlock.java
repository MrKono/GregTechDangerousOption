package kono.ceu.gtdo.mixin;

import static kono.ceu.gtdo.api.util.GTDOValues.explodeMaterialMap;
import static kono.ceu.gtdo.api.util.GTDOValues.explodeWhenWet;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.blocks.BlockMaterialBase;
import gregtech.common.blocks.MaterialItemBlock;

import kono.ceu.gtdo.api.block.IMixinItemBlock;

@Mixin(value = MaterialItemBlock.class, remap = false)
public class MixinMaterialItemBlock extends ItemBlock implements IMixinItemBlock {

    private final BlockMaterialBase block;
    private final OrePrefix prefix;

    public MixinMaterialItemBlock(BlockMaterialBase block, OrePrefix prefix) {
        super(block);
        this.block = block;
        this.prefix = prefix;
        setHasSubtypes(true);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem itemEntity) {
        if (itemEntity.getEntityWorld().isRemote) return false;

        ItemStack stack = itemEntity.getItem();
        int count = itemEntity.getItem().getCount();
        OrePrefix prefix = OreDictUnifier.getPrefix(stack);
        if (prefix != OrePrefix.block) return false;
        if (explodeWhenWet) {
            MaterialStack materialStack = OreDictUnifier.getMaterial(stack);
            if (materialStack != null) {
                Material mat = materialStack.material;
                if (mat instanceof MarkerMaterial) return false;
                if (itemEntity.isWet()) {
                    if (explodeMaterialMap.containsKey(mat)) {
                        float strength = 2.0F * explodeMaterialMap.get(mat) * (1 + ((float) (count - 1) / 100));
                        itemEntity.world.createExplosion(itemEntity, itemEntity.posX, itemEntity.posY, itemEntity.posZ,
                                strength, true);
                        itemEntity.setDead();
                        return false;
                    }
                    return false;
                }
            }
            return false;
        }
        return false;
    }
}
