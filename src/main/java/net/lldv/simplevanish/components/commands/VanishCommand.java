package net.lldv.simplevanish.components.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.simplevanish.SimpleVanish;
import net.lldv.simplevanish.components.language.Language;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand extends PluginCommand<SimpleVanish> {

    private final List<String> vanished = new ArrayList<>();

    public VanishCommand(SimpleVanish owner) {
        super(owner.getConfig().getString("Commands.Vanish.Name"), owner);
        this.setDescription(owner.getConfig().getString("Commands.Vanish.Description"));
        this.setPermission(owner.getConfig().getString("Commands.Vanish.Permission"));
        this.setAliases(owner.getConfig().getStringList("Commands.Vanish.Aliases").toArray(new String[]{}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission(this.getPermission())) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (this.toggleVanish(player)) player.sendMessage(Language.get("vanish.on"));
                    else player.sendMessage(Language.get("vanish.off"));
                    this.updatePlayer(player);
                } else if (args.length == 1) {
                    Player target = this.getPlugin().getServer().getPlayer(args[0]);
                    if (target != null) {
                        if (this.toggleVanish(target)) {
                            target.sendMessage(Language.get("vanish.on"));
                            player.sendMessage(Language.get("vanish.on.other", target.getName()));
                        } else {
                            target.sendMessage(Language.get("vanish.off"));
                            player.sendMessage(Language.get("vanish.off.other", target.getName()));
                        }
                        this.updatePlayer(target);
                    } else player.sendMessage(Language.get("player.offline"));
                } else player.sendMessage(Language.get("usage.vanish", this.getName()));
            } else sender.sendMessage(Language.get("permission.insufficient"));
        }
        return true;
    }

    private boolean toggleVanish(Player player) {
        if (this.vanished.contains(player.getName())) {
            this.vanished.remove(player.getName());
            return false;
        } else {
            this.vanished.add(player.getName());
            return true;
        }
    }

    private void updatePlayer(Player player) {
        this.getPlugin().getServer().getOnlinePlayers().values().forEach(e -> {
            if (e == player) return;
            if (this.vanished.contains(player.getName())) {
                if (this.vanished.contains(e.getName())) e.showPlayer(player);
                else e.hidePlayer(player);
            } else e.showPlayer(player);

            if (this.vanished.contains(e.getName())) {
                if (this.vanished.contains(player.getName())) player.showPlayer(e);
                else player.hidePlayer(e);
            }
        });
    }

}
