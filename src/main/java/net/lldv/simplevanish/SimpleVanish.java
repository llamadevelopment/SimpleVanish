package net.lldv.simplevanish;

import cn.nukkit.plugin.PluginBase;
import net.lldv.simplevanish.components.commands.VanishCommand;
import net.lldv.simplevanish.components.language.Language;

public class SimpleVanish extends PluginBase {

    @Override
    public void onEnable() {
        try {
            this.saveDefaultConfig();
            Language.init(this);
            this.getServer().getCommandMap().register("simplevanish", new VanishCommand(this));
            this.getLogger().info("§aSimpleVanish successfully started.");
        } catch (Exception e) {
            e.printStackTrace();
            this.getLogger().error("§4Failed to load SimpleVanish.");
        }
    }

}
