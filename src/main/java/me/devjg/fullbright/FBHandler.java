package me.devjg.fullbright;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FBHandler {
  private static final KeyBinding FB_BINDING = Fullbright.fullbright;
  private static boolean shouldIncrement;

  private static float nextLevel;
  private static final float DIMMEST = -0.1F;
  private static final float DIM = 3.0F;
  private static final float BRIGHT = 6.5F;
  private static final float BRIGHTEST = 12.0F;

  private static final String TEXT_BASE =
      EnumChatFormatting.DARK_GRAY + "[" +
      EnumChatFormatting.YELLOW + "FullBright" +
      EnumChatFormatting.DARK_GRAY + "] " +
      EnumChatFormatting.GRAY + "set to ";

  FBHandler() {
    nextLevel = getNextLevel();
  }

  private static float getTransitionSpeed() {
    return (float) ((Fullbright.transitionSpeed*6.0)/1000);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRender(RenderHandEvent event) {
    if (FB_BINDING.isPressed()) {
      if ("Instantaneous".equals(Fullbright.getMode())) {
        if (Minecraft.getMinecraft().gameSettings.gammaSetting != nextLevel) {
          float moveBy = nextLevel - Minecraft.getMinecraft().gameSettings.gammaSetting;
          Minecraft.getMinecraft().gameSettings.gammaSetting += moveBy;
          nextLevel = getNextLevel();
        }

        Minecraft.getMinecraft().gameSettings.saveOptions();
      } else if ("Incremental".equals(Fullbright.getMode()))
        shouldIncrement = true;

      if (Fullbright.notifications)
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(getText()));
    }

    if (shouldIncrement) {
      Minecraft.getMinecraft().gameSettings.gammaSetting = lerp(Minecraft.getMinecraft().gameSettings.gammaSetting, nextLevel, getTransitionSpeed());

      if (0.1 >= Math.abs(Minecraft.getMinecraft().gameSettings.gammaSetting - nextLevel)) {
        shouldIncrement = false;
        Minecraft.getMinecraft().gameSettings.saveOptions();
        nextLevel = getNextLevel();
      }
    }
  }

  private static float getNextLevel() {
    float currentLevel = Minecraft.getMinecraft().gameSettings.gammaSetting;
    if (2 >= Math.abs(DIMMEST - currentLevel))
      return DIM;
    else if (2 >= Math.abs(DIM - currentLevel))
      return BRIGHT;
    else if (2 >= Math.abs(BRIGHT - currentLevel))
      return BRIGHTEST;
    return DIMMEST;
  }

  private static String getText() {
    if ("Incremental".equals(Fullbright.getMode())) {
      if (DIMMEST == nextLevel)
        return TEXT_BASE + EnumChatFormatting.DARK_RED + "DIMMEST";
      else if (DIM == nextLevel)
        return TEXT_BASE + EnumChatFormatting.RED + "DIM";
      else if (BRIGHT == nextLevel)
        return TEXT_BASE + EnumChatFormatting.YELLOW + "BRIGHT";
      return TEXT_BASE + EnumChatFormatting.GREEN + "BRIGHTEST";
    } else {
      if (DIMMEST == nextLevel)
        return TEXT_BASE + EnumChatFormatting.GREEN + "BRIGHTEST";
      else if (DIM == nextLevel)
        return TEXT_BASE + EnumChatFormatting.DARK_RED + "DIMMEST";
      else if (BRIGHT == nextLevel)
        return TEXT_BASE + EnumChatFormatting.RED + "DIM";
      return TEXT_BASE + EnumChatFormatting.YELLOW + "BRIGHT";
    }
  }

  private static float lerp(float from, float to, float by) {
    return (float) ((from * (1.0 - by)) + (to * by));
  }
}
