package net.el_mishucha.sculk_cult.networking.pocket;

import net.el_mishucha.sculk_cult.client.infection.ClientInfectionData;
import net.el_mishucha.sculk_cult.client.sculk_charge.ClientSculkChargeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class InfectionDataSyncS2CPocket {
    private final boolean infection;

    public InfectionDataSyncS2CPocket(boolean value) {
        this.infection = value;
    }

    public InfectionDataSyncS2CPocket(FriendlyByteBuf buf) {
        this.infection = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(infection);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientInfectionData.set(infection);
        });
        return true;
    }
}
