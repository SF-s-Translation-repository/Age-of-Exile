package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RenderUtils {

    public static void render16Icon(MatrixStack matrix, Identifier tex, int x, int y) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(tex);
        DrawableHelper.drawTexture(matrix, x, y, 0, 0, 16, 16, 16, 16);
    }

}