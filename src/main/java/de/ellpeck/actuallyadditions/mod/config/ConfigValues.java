/*
 * This file ("ConfigValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config;

import de.ellpeck.actuallyadditions.mod.config.values.*;
import net.minecraftforge.common.config.Configuration;

public final class ConfigValues{

    public static void defineConfigValues(Configuration config){

        for(ConfigCrafting currConf : ConfigCrafting.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, "If the Recipe for the "+currConf.name+" is Enabled").getBoolean();
        }

        for(ConfigIntValues currConf : ConfigIntValues.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc, currConf.min, currConf.max).getInt();
        }

        for(ConfigBoolValues currConf : ConfigBoolValues.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc).getBoolean();
        }

        for(ConfigIntListValues currConf : ConfigIntListValues.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc).getIntList();
        }

        for(ConfigStringListValues currConf : ConfigStringListValues.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc).getStringList();
        }

    }
}
