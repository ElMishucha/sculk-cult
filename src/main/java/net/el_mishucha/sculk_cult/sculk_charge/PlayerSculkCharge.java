package net.el_mishucha.sculk_cult.sculk_charge;

import net.minecraft.nbt.CompoundTag;

public class PlayerSculkCharge {
    static public final float MIN_CHARGE = 0;
    static public final float MAX_CHARGE = 10;
    private float charge = MAX_CHARGE;
    private boolean infected = false;

    public float getCharge() {
        return charge;
    }

    public void addCharge(float add) {
        this.charge = Math.min(charge + add, MAX_CHARGE);
    }

    public void subCharge(float sub) {
        this.charge = Math.max(charge - sub, MIN_CHARGE);
    }

    public boolean getInfected() { return infected; }

    public void setInfected(boolean value) { this.infected = value; }

    public void copyFrom(PlayerSculkCharge source) {
        this.charge = source.charge;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("sculk_charge", charge);
    }

    public void lodNBTData(CompoundTag nbt) {
        charge = nbt.getFloat("sculk_charge");
    }
}
