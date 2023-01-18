package net.el_mishucha.sculk_cult.networking.pocket;

import net.el_mishucha.sculk_cult.client.ClientChargeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargeDataSyncS2CPocket {
    private final float charge;

    public ChargeDataSyncS2CPocket(float charge) {
        this.charge = charge;
    }

    public ChargeDataSyncS2CPocket(FriendlyByteBuf buf) {
        this.charge = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(charge);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientChargeData.set(charge);
        });
        return true;
    }
}
