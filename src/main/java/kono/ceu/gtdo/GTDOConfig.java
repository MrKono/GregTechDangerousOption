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

    public static class FeatureSetting {

        @Config.Comment({ "Whether specific material items explode when wet", "Default: true" })
        public boolean explodeWhenWet = true;
    }

    public static class ExplosionSetting {

        @Config.Comment({ "Material that explodes when wet", "Define material name and power with \":\"." })
        public String[] wetExplosives = new String[] {
                "lithium:1.0", "sodium:2.0", "potassium:4.0", "rubidium:8.0", "caesium:10.0", "francium:12.0"
        };

        @Config.Comment({ "OrePrefix that explodes when wet", "Define OrePrefix and power with \":\".",
                "Explosion Power: OrePrefix's power * Material's power" })
        public String[] wetExplosivesPrefix = new String[] {
                "block:2.0", "ingot:1.0", "nugget:0.1", "dust:1.0", "dustSmall:0.25", "dustTiny:0.1F"
        };
    }
}
