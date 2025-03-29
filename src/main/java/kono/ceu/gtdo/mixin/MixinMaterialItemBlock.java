package kono.ceu.gtdo.mixin;

import static kono.ceu.gtdo.api.util.GTDOValues.*;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

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

    @Unique
    private final BlockMaterialBase block;
    @Unique
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

        MaterialStack materialStack = OreDictUnifier.getMaterial(stack);
        if (materialStack == null || materialStack.material instanceof MarkerMaterial) {
            return false;
        }
        
        Material mat = materialStack.material;
        if (explodeWhenWet) {
            if (!explodePrefixMap.containsKey(prefix) || !itemEntity.isWet() || !explodeMaterialMap.containsKey(mat)) {
                return false;
            }

            float strength = explodePrefixMap.get(prefix) * explodeMaterialMap.get(mat) *
                    (1 + ((float) (count - 1) / 100));
            itemEntity.world.createExplosion(itemEntity, itemEntity.posX, itemEntity.posY, itemEntity.posZ,
                    strength, true);
            itemEntity.setDead();
            return false;
        }
        return false;
    }
}
