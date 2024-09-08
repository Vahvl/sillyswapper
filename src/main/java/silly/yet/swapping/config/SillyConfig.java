package silly.yet.swapping.config;

import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import silly.yet.swapping.SillySwapperMod;

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

    @Switch(
            name = "Only when AOTV held",
            size = OptionSize.SINGLE
    )
    public static boolean AOTVMode = true;

    @KeyBind(
            name = "Swappy keybind"
    )
// using OneKeyBind to set the default key combo to Shift+S
    public static OneKeyBind sillyBind = new OneKeyBind(UKeyboard.KEY_DELETE);

    public SillyConfig() {
        super(new Mod(SillySwapperMod.NAME, ModType.SKYBLOCK), SillySwapperMod.MODID + ".json");
        initialize();
    }
}

