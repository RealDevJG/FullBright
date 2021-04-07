# FullBright
The Future of FullBright


## Download and How To Use
The download of the jar is here: https://www.curseforge.com/minecraft/mc-mods/devjg-fullbright  
To use it press the B button to cycle to the next mode. Using the GUI (/fullbright or /fb), You can change the transition speed.  
Changing the transition speed from ranges 1-3 changes the mode to `incremental`, incremental mode means that when cycling through brightness levels (B), it will fade from your current brightness onto the next. when the transition speed is 0, the mode is set to `instantaneous`. This means that when cycling through brightness levels, they will change instantly instead of having a fade.  
I'm not going to explain how to install it, it's just a forge mod - google it.  

## Discord
For any development related issues/bugs/suggestions please visit: https://discord.gg/b35rQvS  
For any Minecraft Cannon related: https://discord.gg/hk8W88h  

## Change Log
  - Updated to Version 2!
  - Made FullBright open-source
  - Recoded the entire mod to improve things such as performance and the way the fullbright handler works
  - Made the GUI *or I edited it to align things differently, don't remember*
  - Refactored some strings that get sent to the player to fix very slight issues
  - Changed the way the increment speed is worked out
  - Just done some basic fileio to save the transition speed and load it for next restart
  - Changed the brightness level of 3/4 last brightness levels to support the new incremental mode more

## To-Do
  - Add LightFix
  - Add support with all texture packs
  - Shader support (Basically turn fullbright off when shaders are on)
  - Option to toggle chat messages when cycling brightness levels
