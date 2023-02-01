package net.el_mishucha.sculk_cult.player_properties.infection;

import net.minecraft.nbt.CompoundTag;

public class PlayerInfection {
    private boolean infection = false;

    public boolean getInfection() {
        return infection;
    }

    public void setInfection(boolean value) { this.infection = value; }

    public void copyFrom(PlayerInfection source) { this.infection = source.infection; }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("infection", infection);
    }

    public void lodNBTData(CompoundTag nbt) {
        infection = nbt.getBoolean("infection");
    }
}
