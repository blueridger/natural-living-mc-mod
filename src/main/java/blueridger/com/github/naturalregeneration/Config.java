package blueridger.com.github.naturalregeneration;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Work in progress... Needs some design thinking
 *
 */


public class Config {
	public static final ForgeConfigSpec GENERAL_SPEC;
    
	static {
	    ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
	    setupConfig(configBuilder);
	    GENERAL_SPEC = configBuilder.build();
	}

	private static void setupConfig(ForgeConfigSpec.Builder builder) { 
	}
}
