package me.devjg.fullbright.commands;

import me.devjg.fullbright.Fullbright;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class FBCommand extends CommandBase {
  @Override
  public String getCommandName() {
    return "fullbright";
  }

  @Override
  public String getCommandUsage(ICommandSender sender) {
    return "/fullbright";
  }

  @Override
  public List<String> getCommandAliases() {
    return Collections.singletonList("fb");
  }

  @Override
  public void processCommand(ICommandSender sender, String[] args) {
    Fullbright.showGui = true;
  }

  @Override
  public int getRequiredPermissionLevel() {
    return -1;
  }
}
