package kono.ceu.gtdo;

import net.minecraftforge.common.config.Config;

import kono.ceu.gtdo.api.util.GTDOValues;

@Config(modid = GTDOValues.modId)
public class GTDOConfig {

    @Config.Name("Feature Setting")
    @Config.Comment("Settings the features")
    @Config.RequiresMcRestart
    public static FeatureSetting features = new FeatureSetting();

    @Config.Name("Explosion Setting")
    @Config.Comment("Settings the item explosion")
    @Config.RequiresMcRestart
    public static ExplosionSetting explosion = new ExplosionSetting();

    @Config.Name("Fluid Temperature Damage Setting")
    @Config.Comment("Setting the fluid temperature damage")
    @Config.RequiresMcRestart
    public static TemperatureSetting temperature = new TemperatureSetting();

    public static class FeatureSetting {

        @Config.Comment({ "Whether specific material items explode when wet", "Default: true" })
        public boolean explodeWhenWet = true;

        @Config.Comment({ "Whether the temperature of the fluid in the fluid containment item causes damage",
                "Default: true" })
        public boolean temperatureDamage = true;
    }

    public static class ExplosionSetting {

        @Config.Comment({ "Material that explodes when wet", "Define material name and power with \":\"." })
        public String[] wetExplosives = new String[] {
                "lithium:1.0", "sodium:2.0", "potassium:4.0", "rubidium:8.0", "caesium:10.0", "francium:12.0"
        };

        @Config.Comment({ "OrePrefix that explodes when wet", "Define OrePrefix and power with \":\".",
                "Explosion Power: OrePrefix's power * Material's power" })
        public String[] wetExplosivesPrefix = new String[] {
                "block:2.0", "ingot:1.0", "nugget:0.1", "dust:1.0", "dustSmall:0.25", "dustTiny:0.1"
        };
    }

    public static class TemperatureSetting {

        @Config.Comment({ "Whether the damage is done only when held in the main or off hand", "Default: true" })
        public boolean damageOnlyHold = true;

        @Config.Comment({ "List of fluid container items for detection.",
                "Format: \"modID:itemID:metadata\", with metadata being optional." })
        public String[] listFluidContainer = new String[] {
                "minecraft:water_bucket", "minecraft:lava_bucket", "forge:bucketfilled"
        };

        @Config.Comment({ "Deals damage if too hot to hold.", "Default: true" })
        public boolean damageHot = true;

        @Config.Comment({ "Deals damage if too cold to hold.", "Default: true" })
        public boolean damageFrost = true;
    }
}
