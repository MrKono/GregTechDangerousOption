package kono.ceu.gtdo.api.block;

import net.minecraft.entity.item.EntityItem;

public interface IMixinItemBlock {

    boolean onEntityItemUpdate(EntityItem itemEntity);
}
