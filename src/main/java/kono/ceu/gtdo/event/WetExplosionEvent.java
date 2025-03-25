package kono.ceu.gtdo.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import kono.ceu.gtdo.Tags;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class WetExplosionEvent extends Event {

    private final EntityItem itemEntity;

    public WetExplosionEvent(EntityItem itemEntity) {
        this.itemEntity = itemEntity;
    }

    public EntityItem getItemEntity() {
        return itemEntity;
    }

    @SubscribeEvent
    public static void onWetExplosion(WetExplosionEvent event) {
        EntityItem itemEntity = event.getItemEntity();
        World world = itemEntity.getEntityWorld();
        if (!world.isRemote) {
            ItemStack stack = itemEntity.getItem();
            NBTTagCompound tag = stack.getTagCompound();
            float strength = tag.getFloat("waterProhibited");
            itemEntity.world.createExplosion(itemEntity, itemEntity.posX, itemEntity.posY, itemEntity.posZ,
                    strength, true);
            itemEntity.setDead();
        }
    }
}
