package me.saturn.delta;

import dev.tensor.Tensor;
import dev.tensor.backend.events.ClientRenderEvent;
import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.plugin.Plugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author IUDevman
 * @since 10-28-2021
 */

public final class Delta extends Plugin {

    public static Delta INSTANCE;

    public Delta() {
        INSTANCE = this;
    }

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private int timeoutTicks = 0;

    public final Logger LOGGER = LogManager.getLogger("Delta");

    @Override
    public void load() {
        Tensor.INSTANCE.EVENT_HANDLER.register(this);
        this.LOGGER.info("Loaded Delta-Plugin!");
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Receive) return;

        //if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
          //  timeoutTicks = 0;
        //}
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onClientRender(ClientRenderEvent event) {
        if (event.getType() != ClientRenderEvent.Type.HUD) return;

        timeoutTicks++;

        if (timeoutTicks > 150) {
            MatrixStack matrixStack = new MatrixStack();

            matrixStack.push();

            String s = "CRASHING: " + this.timeoutTicks + "ms";

            DrawableHelper.drawTextWithShadow(matrixStack, this.mc.textRenderer, s, (this.mc.getWindow().getScaledWidth() - this.mc.textRenderer.getWidth(s)) / 2, 3, 7289492);
            matrixStack.pop();
        }
    }
}
