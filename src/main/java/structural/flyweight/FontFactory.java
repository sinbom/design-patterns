package structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FontFactory {

    private final Map<String, Font> cache = new HashMap<>();

    public Font getFont(String key) {
        Font font = cache.get(key);

        if (font != null) {
            return font;
        }

        String[] split = key.split(":");
        font = new Font(split[0], Integer.parseInt(split[1]));

        cache.put(key, font);

        return font;
    }

}
