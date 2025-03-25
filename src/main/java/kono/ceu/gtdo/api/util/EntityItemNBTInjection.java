package kono.ceu.gtdo.api.util;

import static kono.ceu.gtdo.api.util.GTDOValues.explodeMaterialMap;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

public class EntityItemNBTInjection {

    public static final Map<OrePrefix, Float> explodePrefixMap = new HashMap<>();

    public static EntityItem checkWaterProhibited(EntityItem itemEntity) {
        ItemStack stack = itemEntity.getItem();
        int count = itemEntity.getItem().getCount();
        OrePrefix prefix = OreDictUnifier.getPrefix(stack);
        if (prefix != null) {
            MaterialStack materialStack = OreDictUnifier.getMaterial(stack);
            if (materialStack != null) {
                Material mat = materialStack.material;
                if (!(mat instanceof MarkerMaterial)) {
                    if (explodePrefixMap.containsKey(prefix)) {
                        float strength = explodePrefixMap.get(prefix) * explodeMaterialMap.get(mat) *
                                (1 + ((float) (count - 1) / 100));
                        NBTTagCompound tag = itemEntity.getItem().getTagCompound();
                        if (tag == null) {
                            tag = new NBTTagCompound();
                        }
                        tag.setFloat("waterProhibited", strength);
                        itemEntity.getItem().setTagCompound(tag);
                    }
                }
            }
        }
        return itemEntity;
    }
}
