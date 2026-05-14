package net.enderman.nhcoins;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class NewHorizonsCoinsClient {

    public NewHorizonsCoinsClient() {
        CommonClassClient.init();
    }
}