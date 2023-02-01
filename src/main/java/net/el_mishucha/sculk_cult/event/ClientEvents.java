package net.el_mishucha.sculk_cult.event;

import net.el_mishucha.sculk_cult.SculkCultMod;
import net.el_mishucha.sculk_cult.client.sculk_charge.SculkChargeHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = SculkCultMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("sculk_charge", SculkChargeHudOverlay.HUD_CHARGE);
        }

    }


}
