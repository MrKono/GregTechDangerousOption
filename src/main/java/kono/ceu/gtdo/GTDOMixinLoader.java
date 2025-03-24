package kono.ceu.gtdo;

import java.util.List;

import com.google.common.collect.Lists;

import zone.rong.mixinbooter.ILateMixinLoader;

public class GTDOMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Lists.newArrayList("mixins.gregtechdangerousoption.json");
    }
}
