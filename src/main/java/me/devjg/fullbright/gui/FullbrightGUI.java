package me.devjg.fullbright.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;
import me.devjg.fullbright.Fullbright;

import java.io.FileWriter;
import java.io.IOException;

public class FullbrightGUI extends GuiScreen {
  @Override
  public void initGui() {
    buttonList.clear();

    buttonList.add(new GuiButton(
        1,
        width / 2 - (150 / 2),
        height / 2 - 40,
        150,
        20,
        "Transition Speed: " + Fullbright.transitionSpeed));
    buttonList.add(new GuiButton(
        2,
        width - 130,
        height - 25,
        125,
        20,
        "Discord"));
    buttonList.add(new GuiButton(
        3,
        width - 130,
        height - 50,
        125,
        20,
        "Notifications: " + Fullbright.notifications));

    super.initGui();
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    drawDefaultBackground();
    super.drawScreen(mouseX, mouseY, partialTicks);

    GL11.glPushMatrix();
    GL11.glScalef(2.0F, 2.0F, 2.0F);
    drawCenteredString(fontRendererObj, "The Future Of", width / 4, 2, 0x596891);
    drawCenteredString(fontRendererObj, "FullBright", width / 4, 12, 0x596891);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glScalef(1.0F, 1.0F, 1.0F);
    drawCenteredString(fontRendererObj,
        "Mode: " + Fullbright.getMode(),
        width / 2,
        height / 2 - 55,
        0x596891);
    GL11.glPopMatrix();
  }

  @Override
  public void onGuiClosed() {
    Fullbright.saveData();
  }

  @Override
  protected void actionPerformed(GuiButton pressedButton) {
    switch (pressedButton.id) {
      case 1:
        if (3 == Fullbright.transitionSpeed)
          Fullbright.transitionSpeed = 0;
        else
          Fullbright.transitionSpeed++;

        mc.displayGuiScreen(new FullbrightGUI());
        break;
      case 2:
        mc.displayGuiScreen(null);

        IChatComponent url = new ChatComponentText(
            EnumChatFormatting.DARK_GRAY + "[" +
                EnumChatFormatting.YELLOW + "FullBright" +
                EnumChatFormatting.DARK_GRAY + "] " +
                EnumChatFormatting.GRAY + "Click here to join the discord!");

        url.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/b35rQvS"));
        mc.thePlayer.addChatMessage(url);
        break;
      case 3:
        Fullbright.notifications = !Fullbright.notifications;
        mc.displayGuiScreen((new FullbrightGUI()));
    }
  }
}
