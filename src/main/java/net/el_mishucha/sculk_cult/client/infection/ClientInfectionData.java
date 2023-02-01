package net.el_mishucha.sculk_cult.client.infection;

public class ClientInfectionData {
    private static boolean playerInfection;

    public static void set(boolean value) {
        ClientInfectionData.playerInfection = value;
    }

    public static boolean getPlayerCharge() {
        return ClientInfectionData.playerInfection;
    }
}
