Hetzer Wars
Aayush Kotharu, Raghava Ravi
Period 1
05/23/16

  _    _      _                 __          __            
 | |  | |    | |                \ \        / /            
 | |__| | ___| |_ _______ _ __   \ \  /\  / /_ _ _ __ ___ 
 |  __  |/ _ \ __|_  / _ \ '__|   \ \/  \/ / _` | '__/ __|
 | |  | |  __/ |_ / /  __/ |       \  /\  / (_| | |  \__ \
 |_|  |_|\___|\__/___\___|_|        \/  \/ \__,_|_|  |___/
                                                          
                                                          
README:
Commander, a war has been declared on the bordering nation. The fate of our nation 
rests upon your shoulder. Hone your skills, oil your tank, and gear up for battle.

In this 2-player game, players will navigate tanks across the map in order to take
their opponent’s tanks while sustaining the least damage possible to themselves. 
Players start at different locations of the map and must navigate around the walls.
Gain advantages through the powerups and powerdowns that appear throughout the game 
and with the various timed boosts. Wipe out the enemy and be the last survivor 
in order to save your nation. Control center signing out.


Instructions:
 -Players are assigned a tank and begin at a differnet location the map. 
 -Player use the ‘W’ and "Up Arrow" keys to move the tank forward and the ‘S’ and
  "Down Arrow" keys to move backward. The ‘A’ and "Left Arrow" keys to turn the 
  tank left in place and the ‘D’ and "Right Arrow" keys to turn the tank right in 
  place. ( Player 1 uses WASD, Player 2 used the direction arrow keys)
 -Player using WASD, shoots bullets with the 'V' key. Player using the directional
  arrows shoots with the 'M' key.
 -Tanks have a maximum health of 100.
	-Missiles do -20 damage
	-Bombs do -40 damage
	-Hearts add 20 to health

Objective of game.
Take out the enemy tank and sustain the minimum damage possible in order be the 
winner. Use the walls and powerups/bombs to your advantage.

Features:
Must-have features:
-Starting menu with instruction button and a play button (done)
-Large map with multiple walls (done)
-Power ups (done)
-Health bar (done)
-Individually controllable tank that fires with keylistener (done)

Want-to-have features:
-Networking (2 player)
-Background music (done)
-Powerups and PowerDowns Power ups (done)
-Pop-up instructions in new window (done)
-Ability for player to customize tank color with a slider menu
-More graphical detail to map.
-Rumbling sound while tanking is moving, sound of tank being hit. (partially done)
-Multiple levels (done)
-Mini bar at bottom with player names, direction, speedometer
-Option to quit game- self destructs

Stretch features:
-Networking (4 player)
   -Text chat for players
-Collision music (done)
-More maps- chosen randomly at each match making
-Camouflage powerup that makes tanks hidden for some amount of time
-Tanks glow/some indication that it is on a powerup



Class List:
Package: Components
Tank: Represents tank objects
Bullet: Represents bullets
Wall: Barrier that is a game object
PowerUp: Represents a health powerup
Bomb: Represent a bomb object 
GameObjects: A superclass for things that go on the map (including moving objects)
Sound: Hold static fields of sound objects

Package: GUI
Main: Instantiates a new game
PanelHolder: holds all panels within the game (Extends JFrame)
GamePanel: Holds all other panels in a cardlayout
MenuPanel: A panel that shows start button and instruction button
WinnerPanel: A panel that shows the player who has won the game


Responsibilities list:
Raghava: Components
Aayush: GUI