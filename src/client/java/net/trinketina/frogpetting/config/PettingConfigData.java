package net.trinketina.frogpetting.config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "frog_petting")
public class PettingConfigData implements ConfigData {

    @ConfigEntry.Gui.PrefixText()
    public int COOLDOWN = 5;
    public List<String> IGNORED_MOBS = new ArrayList<>();

    @ConfigEntry.Gui.PrefixText()
    public boolean ENABLE_KEYBIND = false;

}
