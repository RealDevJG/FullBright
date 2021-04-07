package me.devjg.fullbright;

import me.devjg.fullbright.commands.FBCommand;
import me.devjg.fullbright.gui.FullbrightGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Mod(modid = "devjg_fullbright", version = "2.0.0", acceptedMinecraftVersions = "[1.8,]")
public class Fullbright {
  public static File settingsFile;
  public static float transitionSpeed = 0F;
  public static boolean showGui;
  public static KeyBinding fullbright;

  public static float getIncrementSpeed() {
    return (transitionSpeed/1000*transitionSpeed)/2;
  }

  public static String getMode() {
    return transitionSpeed == 0 ? "Instantaneous" : "Incremental";
  }

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    settingsFile = new File("DevJG_FullBright.txt");
    try {
      Scanner settingsScanner = new Scanner(settingsFile);
      if (settingsScanner.hasNextLine()) {
        transitionSpeed = settingsScanner.nextFloat();
        settingsScanner.close();
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);

    fullbright = new KeyBinding("Cycle Brightness Levels", Keyboard.KEY_B, "discord.gg/b35rQvS");
    ClientRegistry.registerKeyBinding(fullbright);

    MinecraftForge.EVENT_BUS.register(new FBHandler());
    ClientCommandHandler.instance.registerCommand(new FBCommand());
  }

  @SubscribeEvent
  public void onTick(TickEvent.ClientTickEvent event) {
    if (showGui && event.phase == TickEvent.Phase.END) {
      Minecraft.getMinecraft().displayGuiScreen(new FullbrightGUI());
      showGui = false;
    }
  }
}
