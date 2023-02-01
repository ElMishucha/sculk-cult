package net.el_mishucha.sculk_cult.networking;

import net.el_mishucha.sculk_cult.SculkCultMod;
import net.el_mishucha.sculk_cult.networking.pocket.InfectionDataSyncS2CPocket;
import net.el_mishucha.sculk_cult.networking.pocket.SculkChargeDataSyncS2CPocket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SculkCultMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SculkChargeDataSyncS2CPocket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SculkChargeDataSyncS2CPocket::new)
                .encoder(SculkChargeDataSyncS2CPocket::toBytes)
                .consumerMainThread(SculkChargeDataSyncS2CPocket::handle)
                .add();
        net.messageBuilder(InfectionDataSyncS2CPocket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(InfectionDataSyncS2CPocket::new)
                .encoder(InfectionDataSyncS2CPocket::toBytes)
                .consumerMainThread(InfectionDataSyncS2CPocket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}