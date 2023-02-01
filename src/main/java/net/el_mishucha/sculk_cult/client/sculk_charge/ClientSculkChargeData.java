package net.el_mishucha.sculk_cult.client.sculk_charge;

public class ClientSculkChargeData {
    private static float playerCharge;

    public static void set(float charge) {
        ClientSculkChargeData.playerCharge = charge;
    }

    public static float getPlayerCharge() {
        return ClientSculkChargeData.playerCharge;
    }
}
