package silly.yet.swapping.config;

import silly.yet.swapping.SillySwapper;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See <a href="https://docs.polyfrost.cc/oneconfig/config/adding-options">this link</a> for more config Options
 */
public class SillyConfig extends Config {

    @Switch(
            name = "Enable thingamabob!",
            size = OptionSize.SINGLE // Optional
    )
    public static boolean sillySwapper = false; // The default value for the boolean Switch.

    public SillyConfig() {
        super(new Mod(SillySwapper.NAME, ModType.SKYBLOCK), SillySwapper.MODID + ".json");
        initialize();
    }
}

