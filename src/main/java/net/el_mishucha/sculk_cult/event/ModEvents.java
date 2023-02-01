package net.el_mishucha.sculk_cult.event;

import com.mojang.logging.LogUtils;
import net.el_mishucha.sculk_cult.SculkCultMod;
import net.el_mishucha.sculk_cult.networking.ModMessages;
import net.el_mishucha.sculk_cult.networking.pocket.InfectionDataSyncS2CPocket;
import net.el_mishucha.sculk_cult.networking.pocket.SculkChargeDataSyncS2CPocket;
import net.el_mishucha.sculk_cult.player_properties.infection.PlayerInfection;
import net.el_mishucha.sculk_cult.player_properties.infection.PlayerInfectionProvider;
import net.el_mishucha.sculk_cult.player_properties.sculk_charge.PlayerSculkCharge;
import net.el_mishucha.sculk_cult.player_properties.sculk_charge.PlayerSculkChargeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = SculkCultMod.MOD_ID)
public class ModEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        event.player.setSprinting(false);

        event.player.getFoodData().setFoodLevel(1);

        if(event.side == LogicalSide.SERVER) {
            event.player.setSprinting(false);

            event.player.getFoodData().setFoodLevel(1);

            event.player.getCapability(PlayerSculkChargeProvider.PLAYER_CHARGE).ifPresent(charge -> {
                charge.subCharge(0.05f / (5 * 60)); // 1 / 0.05 == 1 second
                ModMessages.sendToPlayer(new SculkChargeDataSyncS2CPocket(charge.getCharge()), ((ServerPlayer) event.player));

            });

            event.player.getCapability(PlayerInfectionProvider.PLAYER_INFECTION).ifPresent(infection -> {
                event.player.sendSystemMessage(Component.literal(String.valueOf(infection.getInfection())));
            });
        }

    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerSculkChargeProvider.PLAYER_CHARGE).isPresent()) {
                event.addCapability(new ResourceLocation(SculkCultMod.MOD_ID, "properties"), new PlayerSculkChargeProvider());
            }
            if(!event.getObject().getCapability(PlayerInfectionProvider.PLAYER_INFECTION).isPresent()) {
                event.addCapability(new ResourceLocation(SculkCultMod.MOD_ID, "properties"), new PlayerInfectionProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerSculkChargeProvider.PLAYER_CHARGE).ifPresent(newStore -> {
                newStore.copyFrom(new PlayerSculkCharge());
            });

            event.getOriginal().getCapability(PlayerInfectionProvider.PLAYER_INFECTION).ifPresent(newStore -> {
                newStore.setInfection(false);
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerSculkCharge.class);
        event.register(PlayerInfection.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerSculkChargeProvider.PLAYER_CHARGE).ifPresent(charge -> {
                    ModMessages.sendToPlayer(new SculkChargeDataSyncS2CPocket(charge.getCharge()), player);
                });
//                player.getCapability(PlayerInfectionProvider.PLAYER_INFECTION).ifPresent(infection -> {
//                    ModMessages.sendToPlayer(new InfectionDataSyncS2CPocket(infection.getInfection()), player);
//                });
            }
        }
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type())
            event.setCanceled(true);
    }

}
