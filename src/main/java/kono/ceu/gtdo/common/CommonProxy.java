package kono.ceu.gtdo.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.registries.IForgeRegistry;

import gregtech.api.util.EntityDamageUtil;
import gregtech.loaders.recipe.RecyclingRecipes;

import kono.ceu.gtdo.api.util.GTDOValues;

@Mod.EventBusSubscriber(modid = GTDOValues.modId)
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerRecipesHighest(RegistryEvent.Register<IRecipe> event) {}

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerRecipesHigh(RegistryEvent.Register<IRecipe> event) {}

    @SubscribeEvent
    public static void registerRecipesNormal(RegistryEvent.Register<IRecipe> event) {}

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerRecipesLow(RegistryEvent.Register<IRecipe> event) {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        RecyclingRecipes.init();
    }

    @SubscribeEvent
    public static void temperatureDamage(TickEvent.PlayerTickEvent event) {
        if (!GTDOValues.temperatureDamage) return;
        if (GTDOValues.fluidContainer.isEmpty()) return;
        if (event.player.world.isRemote) return;
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.ticksExisted % 20 != 0) return;

        EntityPlayer player = event.player;
        List<ItemStack> stacks = new ArrayList<>();
        stacks.clear();
        if (GTDOValues.tempDamageOnlyHold) {
            ItemStack main = player.getHeldItemMainhand();
            stacks.add(main);
            ItemStack off = player.getHeldItemOffhand();
            stacks.add(off);
        } else {
            stacks.addAll(player.inventory.mainInventory);
        }
        for (ItemStack stack : stacks) {
            if (GTDOValues.fluidContainer.contains(stack.getItem())) {
                IFluidHandlerItem fluidHandlerItem = stack
                        .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (fluidHandlerItem != null) {
                    for (IFluidTankProperties properties : fluidHandlerItem.getTankProperties()) {
                        FluidStack containerFluid = properties.getContents();
                        if (containerFluid != null && containerFluid.amount > 0) {
                            int temp = containerFluid.getFluid().getTemperature();
                            if ((GTDOValues.heatDamage && temp > 320) || (GTDOValues.frostDamage && temp < 260)) {
                                EntityDamageUtil.applyTemperatureDamage(player, temp, 1.0F, 2);
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * public void setupExplosion() {
     * MinecraftForge.EVENT_BUS.register(this);
     * }
     * 
     * @SubscribeEvent
     * public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
     * if (event.getEntity() instanceof EntityItem) {
     * EntityItem itemEntity = (EntityItem) event.getEntity();
     * checkWaterProhibited(itemEntity);
     * }
     * }
     * 
     * @SubscribeEvent
     * public static void explosionEvent(TickEvent.WorldTickEvent event) {
     * if (event.phase == TickEvent.Phase.END) {
     * for (EntityItem itemEntity : event.world.getEntities(EntityItem.class, e -> true)) {
     * ItemStack stack = itemEntity.getItem();
     * if (stack.hasTagCompound()) {
     * if (stack.getTagCompound().hasKey("waterProhibited")) {
     * if (itemEntity.isWet()) {
     * MinecraftForge.EVENT_BUS.post(new WetExplosionEvent(itemEntity));
     * }
     * }
     * }
     * }
     * }
     * }
     */
}
