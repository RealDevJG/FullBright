# FullBright
The Future of FullBright

## Download and How To Use
The download to the jar is here: https://www.curseforge.com/minecraft/mc-mods/devjg-fullbright  
To use it press the B button to cycle to the next mode. Using the GUI (/fullbright or /fb), You can change the transition speed.  
Changing the transition speed from ranges 1-3 changes the mode to `incremental`, incremental mode means that when cycling through brightness levels (B), it will fade from your current brightness onto the next. when the transition speed is 0, the mode is set to `instantaneous`. This means that when cycling through brightness levels, they will change instantly instead of having a fade.  
I'm not going to explain how to install it, it's just a forge mod - google it.  

## Discord
For any development related issues/bugs/suggestions please visit: https://discord.gg/b35rQvS  
For any Minecraft Cannon related: https://discord.gg/hk8W88h  

## Changelog Version 2.1.0:
  - Changed the code a lot
  - Changed increment/transition speeds so they are faster
  - Added linear interpolation while changing between brightness levels to make it more bearable
  - Added chat notification toggle button
  - Changed the name of the keybinding in the controls menu
  - Changed the highest brightness level to be slightly higher than in the previous version to avoid blocklight level 0 still appearing to be very very slightly dark 

## Changelog Version 2.1.1:
  - Fixed the bug where the nextLevel to transition to isn't properly defined on game launch

## To-Do
  - Add LightFix
  - Add support with all texture packs
  - Shader support
  - Auto-adjust FullBright (maybe)
  - Add a slider instead of 3 options to choose the transition speed custom
