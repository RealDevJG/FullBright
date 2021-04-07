package me.devjg.fullbright;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FBHandler {
  private static final KeyBinding FB_BINDING = Fullbright.fullbright;
  private static boolean shouldIncrement;

  private static float nextLevel;
  private static final float DIMMEST = 0F;
  private static final float DIM = 2F;
  private static final float BRIGHT = 6F;
  private static final float BRIGHTEST = 10F;

  private static final String TEXT_BASE =
      EnumChatFormatting.DARK_GRAY + "[" +
      EnumChatFormatting.YELLOW + "FullBright" +
      EnumChatFormatting.DARK_GRAY + "] " +
      EnumChatFormatting.GRAY + "set to ";

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRender(RenderHandEvent event) {
    if (FB_BINDING.isPressed()) {
      nextLevel = getNextLevel();
      if (Fullbright.getMode().equals("Instantaneous")) {
        if (Minecraft.getMinecraft().gameSettings.gammaSetting != nextLevel) {
          float moveBy = nextLevel - Minecraft.getMinecraft().gameSettings.gammaSetting;
          Minecraft.getMinecraft().gameSettings.gammaSetting += moveBy;

          Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(getNeededText()));
        }
        Minecraft.getMinecraft().gameSettings.saveOptions();
      }
      else if (Fullbright.getMode().equals("Incremental")) {
        shouldIncrement = true;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(getNeededText()));
      }
    }
    if (shouldIncrement) {
      if (Minecraft.getMinecraft().gameSettings.gammaSetting < nextLevel) {
        Minecraft.getMinecraft().gameSettings.gammaSetting += Fullbright.getIncrementSpeed();
        Minecraft.getMinecraft().gameSettings.saveOptions();

        if (Minecraft.getMinecraft().gameSettings.gammaSetting >= nextLevel)
          shouldIncrement = false;
      } else {
        Minecraft.getMinecraft().gameSettings.gammaSetting -= Fullbright.getIncrementSpeed();
        Minecraft.getMinecraft().gameSettings.saveOptions();

        if (Minecraft.getMinecraft().gameSettings.gammaSetting <= nextLevel) {
          Minecraft.getMinecraft().gameSettings.gammaSetting = DIMMEST;
          shouldIncrement = false;
        }
      }
    }
  }

  private static float getNextLevel() {
    if (Minecraft.getMinecraft().gameSettings.gammaSetting <= DIMMEST)
      return DIM;
    else if (Minecraft.getMinecraft().gameSettings.gammaSetting <= DIM+0.02)
      return BRIGHT;
    else if (Minecraft.getMinecraft().gameSettings.gammaSetting <= BRIGHT+0.02)
      return BRIGHTEST;
    else
      return DIMMEST;
  }

  private static String getNeededText() {
    if (nextLevel == DIMMEST)
      return TEXT_BASE + EnumChatFormatting.DARK_RED + "DIMMEST";
    else if (nextLevel == DIM)
      return TEXT_BASE + EnumChatFormatting.RED + "DIM";
    else if (nextLevel == BRIGHT)
      return TEXT_BASE + EnumChatFormatting.YELLOW + "BRIGHT";
    else
      return TEXT_BASE + EnumChatFormatting.GREEN + "BRIGHTEST";
  }
}
