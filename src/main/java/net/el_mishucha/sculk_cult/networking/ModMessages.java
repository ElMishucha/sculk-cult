package net.el_mishucha.sculk_cult.networking;

import net.el_mishucha.sculk_cult.SculkCultMod;
import net.el_mishucha.sculk_cult.networking.pocket.ChargeDataSyncS2CPocket;
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

        net.messageBuilder(ChargeDataSyncS2CPocket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ChargeDataSyncS2CPocket::new)
                .encoder(ChargeDataSyncS2CPocket::toBytes)
                .consumerMainThread(ChargeDataSyncS2CPocket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}