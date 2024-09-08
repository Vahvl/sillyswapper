package silly.yet.swapping;

import net.minecraftforge.common.MinecraftForge;
import silly.yet.swapping.config.SillyConfig;
import cc.polyfrost.oneconfig.events.event.InitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * The entrypoint of the Example Mod that initializes it.
 *
 * @see Mod
 * @see InitializationEvent
 */
@Mod(modid = SillySwapperMod.MODID, name = SillySwapperMod.NAME, version = SillySwapperMod.VERSION)
public class SillySwapperMod {

    // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
    public static final String MODID = "sillyswapper";
    public static final String NAME = "SillySwapper";
    public static final String VERSION = "1.1";
    @Mod.Instance(MODID)
    public static SillySwapperMod INSTANCE; // Adds the instance of the mod, so we can access other variables.
    public static SillyConfig config;

    // Register the config and commands.
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new SillyConfig();
        MinecraftForge.EVENT_BUS.register(new SillySwapping());
    }
}
