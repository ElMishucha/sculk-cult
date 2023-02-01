package net.el_mishucha.sculk_cult.client.sculk_charge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.el_mishucha.sculk_cult.SculkCultMod;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SculkChargeHudOverlay {
    private static final ResourceLocation FILLED_CHARGE = new ResourceLocation(SculkCultMod.MOD_ID,
            "textures/sculk_charge/filled_charge.png");
    private static final ResourceLocation EMPTY_CHARGE = new ResourceLocation(SculkCultMod.MOD_ID,
            "textures/sculk_charge/empty_charge.png");

    private static final ResourceLocation HALF_CHARGE = new ResourceLocation(SculkCultMod.MOD_ID,
            "textures/sculk_charge/half_charge.png");

    public static IGuiOverlay HUD_CHARGE = ((gui, poseStack, partialTick, width, height) -> {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            int x = width / 2;
            int y = height;

            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, EMPTY_CHARGE);

            for (int i = 0; i < 10; i++) {
                GuiComponent.blit(poseStack, x + 10 + (i * 8), y - 39, 0, 0,
                        9, 9, 9, 9);
            }

            RenderSystem.setShaderTexture(0, FILLED_CHARGE);
            for (int i = 0; i < 10; i++) {
                if ((int) ClientSculkChargeData.getPlayerCharge() > i) {
                    GuiComponent.blit(poseStack, x + 10 + (i * 8), y - 39, 0, 0,
                            9, 9, 9, 9);
                } else {
                    break;
                }
            }

            RenderSystem.setShaderTexture(0, HALF_CHARGE);
            GuiComponent.blit(poseStack, x + 10 + (int) ClientSculkChargeData.getPlayerCharge() * 8, y - 39, 0, 0,
                    9, 9, 9, 9);
        }
    });
}
