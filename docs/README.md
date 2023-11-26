# LDTS_l04gr05 - ForwardWarfare

## GAME DESCRIPTION

FowardWarfare is our recreation of the game called Advance Wars. It’s a series of turn-based strategy games developed by Intelligent Systems and published by Nintendo. The game was first released for the Game Boy Advance and features tactical battles between armies on a grid-based map. It offers challenging gameplay where players command various military units such as infantry, tanks, aircraft, and ships, each with unique abilities and characteristics. The objective is to defeat the enemy army, capture bases, and control cities to obtain resources and additional units. 

For LDTS 2023/24, this project was developed by:
- Filipe Esteves up202206515@up.pt
- Gabriel Lima up202206693@up.pt
- Renata Simão up202205124@up.pt

## IMPLEMENTED FEATURES

- **Map** - Loads a custom map using the format .fw, where a number corresponds to a tile.
  (currently only working by editing the file 1.fw)

## PLANNED FEATURES

- **Color** - you can choose the color of your player.

- **Map select** - Select the map you want to use.

- **Play**  - you can play against AI or other person (like a friend for instance).

- **Interact with facilities to collect resources:**

  - **Collect coins** - a facility will give the game character coins in each round of the game.

  - **Use coins to get more troops**.

- **Defeat the base** - defeat your opponent's base to win the game.

## DESIGN

### Playable elements / PlayableFactory

**Problem in Context.**

There is a lot of playable types, so it's difficult to see at a glance the available troops.
And a factory facility needs to create new troops with ease.

**The Pattern.**

So, a Factory pattern is perfect in this situation. It allows us to have a single class that reunites all the available troops and lets other classes get a new troop without having to know about all the available troops.

**Implementation.**

Not yet implemented
