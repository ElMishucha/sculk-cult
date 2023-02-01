package net.el_mishucha.sculk_cult.networking.pocket;

import net.el_mishucha.sculk_cult.client.sculk_charge.ClientSculkChargeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SculkChargeDataSyncS2CPocket {
    private final float charge;

    public SculkChargeDataSyncS2CPocket(float charge) {
        this.charge = charge;
    }

    public SculkChargeDataSyncS2CPocket(FriendlyByteBuf buf) {
        this.charge = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(charge);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientSculkChargeData.set(charge);
        });
        return true;
    }
}
