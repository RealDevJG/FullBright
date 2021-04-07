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
    super.initGui();
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    drawDefaultBackground();
    super.drawScreen(mouseX, mouseY, partialTicks);

    GL11.glPushMatrix();
    GL11.glScalef(2F, 2F, 2F);
    drawCenteredString(fontRendererObj, "The Future Of", width / 4, 2, 0x596891);
    drawCenteredString(fontRendererObj, "FullBright", width / 4, 12, 0x596891);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glScalef(1F, 1F, 1F);
    drawCenteredString(fontRendererObj,
        "Mode: " + Fullbright.getMode(),
        width / 2,
        height / 2 - 55,
        0x596891);
    GL11.glPopMatrix();
  }

  @Override
  public void onGuiClosed() {
    try {
      FileWriter settingsWriter = new FileWriter("DevJG_FullBright.txt");
      settingsWriter.write(String.valueOf(Fullbright.transitionSpeed));
      settingsWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void actionPerformed(GuiButton pressedButton) {
    if (pressedButton.id == 1) {
      if (Fullbright.transitionSpeed != 3)
        Fullbright.transitionSpeed++;
      else
        Fullbright.transitionSpeed = 0;
      mc.displayGuiScreen(new FullbrightGUI());
    }

    else if (pressedButton.id == 2) {
      mc.displayGuiScreen(null);

      IChatComponent url = new ChatComponentText(
      EnumChatFormatting.DARK_GRAY + "[" +
          EnumChatFormatting.YELLOW + "FullBright" +
          EnumChatFormatting.DARK_GRAY + "] " +
          EnumChatFormatting.GRAY + "Click here to join the discord!");

      url.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/b35rQvS"));
      mc.thePlayer.addChatMessage(url);
    }
  }
}
