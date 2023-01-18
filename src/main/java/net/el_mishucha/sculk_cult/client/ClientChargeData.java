package net.el_mishucha.sculk_cult.client;

public class ClientChargeData {
    private static float playerCharge;

    public static void set(float charge) {
        ClientChargeData.playerCharge = charge;
    }

    public static float getPlayerCharge() {
        return ClientChargeData.playerCharge;
    }
}
