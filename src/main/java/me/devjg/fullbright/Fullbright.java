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
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Mod(modid = "devjg_fullbright", version = "2.1.0", acceptedMinecraftVersions = "[1.8,]")
public class Fullbright {
  public static float transitionSpeed;
  public static boolean showGui;
  public static boolean notifications = true;
  static KeyBinding fullbright;

  public static String getMode() {
    return 0 == transitionSpeed ? "Instantaneous" : "Incremental";
  }

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    File settingsFile = new File("DevJG_FullBright.txt");
    try {
      Scanner settingsScanner = new Scanner(settingsFile);

      if (2 == Files.lines(Paths.get("DevJG_FullBright.txt")).count()) {
        transitionSpeed = settingsScanner.nextFloat();
        notifications = settingsScanner.nextBoolean();
      } else {
        saveData();
      }

      settingsScanner.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);

    fullbright = new KeyBinding("Next Brightness Level", Keyboard.KEY_B, "discord.gg/b35rQvS");
    ClientRegistry.registerKeyBinding(fullbright);

    MinecraftForge.EVENT_BUS.register(new FBHandler());
    ClientCommandHandler.instance.registerCommand(new FBCommand());
  }

  @SubscribeEvent
  public void onTick(TickEvent.ClientTickEvent event) {
    if (showGui && TickEvent.Phase.END == event.phase) {
      Minecraft.getMinecraft().displayGuiScreen(new FullbrightGUI());
      showGui = false;
    }
  }

  public static void saveData() {
    try {
      FileWriter settingsWriter = new FileWriter("DevJG_FullBright.txt");
      settingsWriter.write(String.valueOf(Fullbright.transitionSpeed) + "\n" + String.valueOf(Fullbright.notifications));
      settingsWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
