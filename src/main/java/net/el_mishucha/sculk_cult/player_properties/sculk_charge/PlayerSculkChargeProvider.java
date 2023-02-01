package net.el_mishucha.sculk_cult.player_properties.sculk_charge;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerSculkChargeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSculkCharge> PLAYER_CHARGE = CapabilityManager.get(new CapabilityToken<PlayerSculkCharge>() {} );

    private PlayerSculkCharge charge = null;
    private final LazyOptional<PlayerSculkCharge> optional = LazyOptional.of(this::createPlayerSculkCharge);

    private PlayerSculkCharge createPlayerSculkCharge() {
        if (this.charge == null) {
            this.charge = new PlayerSculkCharge();
        }

        return this.charge;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_CHARGE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSculkCharge().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSculkCharge().lodNBTData(nbt);
    }
}
