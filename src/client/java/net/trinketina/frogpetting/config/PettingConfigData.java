package net.trinketina.frogpetting.config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "frog_petting")
public class PettingConfigData implements ConfigData {

    @ConfigEntry.Gui.PrefixText()
    public int COOLDOWN = 10;
    @ConfigEntry.Gui.Tooltip
    public List<String> IGNORED_MOBS = new ArrayList<>();

    @ConfigEntry.Gui.Tooltip()
    public boolean DISABLE_RIGHT_CLICK_PET = false;

}