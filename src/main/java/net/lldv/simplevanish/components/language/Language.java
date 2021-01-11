package net.lldv.simplevanish.components.language;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.Map;

public class Language {

    public static HashMap<String, String> messages = new HashMap<>();
    public static String prefix;

    public static void init(PluginBase instance) {
        messages.clear();
        instance.saveResource("messages.yml");
        Config m = new Config(instance.getDataFolder() + "/messages.yml");
        for (Map.Entry<String, Object> map : m.getAll().entrySet()) {
            String key = map.getKey();
            if (map.getValue() instanceof String) {
                String val = (String) map.getValue();
                messages.put(key, val);
            }
        }
        prefix = m.getString("prefix");
    }

    public static String get(String key, Object... replacements) {
        String message = prefix.replace("&", "§") + messages.getOrDefault(key, "null").replace("&", "§");

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }

    public static String getNP(String key, Object... replacements) {
        String message = messages.getOrDefault(key, "null").replace("&", "§");

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }

}
