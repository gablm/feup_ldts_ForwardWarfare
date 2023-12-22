# LDTS_l04gr05 - ForwardWarfare

## GAME DESCRIPTION

FowardWarfare is our recreation of the game called Advance Wars. It’s a series of turn-based strategy games developed by Intelligent Systems and published by Nintendo. The game was first released for the Game Boy Advance and features tactical battles between armies on a grid-based map. It offers challenging gameplay where players command various military units such as infantry, tanks, aircraft, and ships, each with unique abilities and characteristics. The objective is to defeat the enemy army, capture bases, and control facilities to obtain resources and additional units. 

For LDTS 2023/24, this project was developed by:
- Filipe Esteves up202206515@up.pt
- Gabriel Lima up202206693@up.pt
- Renata Simão up202205124@up.pt

## HOW TO PLAY

### \*\*MISSING\*\*

## IMPLEMENTED FEATURES

- **Map** - Loads a custom map using the format .fw, where a number corresponds to a tile.
  (currently only working by editing the file 1.fw)
  
- **Color** - you can choose the color of your player.

- **Map select** - Select the map you want to use.

- **Play**  - you can play against AI or other person (like a friend for instance).

- **Interact with facilities to collect resources:**

  - **Collect coins** - a facility will give the game character coins in each round of the game.

  - **Use coins to get more troops**.

- **Defeat the base** - defeat your opponent's base to win the game.

- **Capture facilities:**
    - **Not owned**
    - **Owned by other player**

- **Kill other player troops**

- **Recovery** - Base recovers lifes if not attacked in two consecutive rounds

- **Movement** - move troops in the map, in order to reach other troops/facilities/base.

## PLANNED FEATURES

All the planned features were successfully implemented.

## MOCKUPS

### \*\*CHANGE TO SCREENSHOTS\*\*

## DESIGN

### UML DIAGRAM

### \*\*ADD UML && IMPROVE DESIGN CHOICES\*\*

### Tiles and Playable troops are similar in base composition

- **Problem in Context:** tiles and Playable troops are quite similar in their base composition but differ only in some aspects, that being that troops can move and have life points, and that each tile does not have such funcionality.
  
- **The Pattern:** the decorator pattern is extremely useful in this case. By creating an abstract class, we can "decorate" the class with the required functions to make a troop playable.

- **Implementation:**

  Represented in Yellow in the UML.

  [Base abstract class - Element](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Element.java) |
  [Decorator abstract class - Playable](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Playable/Playable.java) |
  [Class that extends Playable - HeavyTank](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Playable/Ground/HeavyTank.java)
  
- **Consequences:** by having this implementation, every element in the map can be reduced to the Element type and it the functionality of each troop will remain if they are accessed thru a type cast.

### A water or field tile might contain a facility

- **Problem in Context:** a tile in our game, if of water or field type, might contain a facility. As there is various types of facilities, there need to be a way for the tile to store what facility is currently inside it.

- **The Pattern:** for this we choose the strategy pattern. By using it, we can define the facilities as "strategies" of how a tile behaves when interacted with by a player.

- **Implementation:**
  
  Represented in Green in the UML.

  [Field](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Tile/Fields.java) |
  [Water](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Tile/Water.java) |
  [Facility](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Facility/Facility.java) |
  [Factory](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Facility/Factory.java) |
  [OilPump](https://github.com/FEUP-LDTS-2023/project-l04gr05/blob/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Facility/OilPump.java) |
  [Other facilities](https://github.com/FEUP-LDTS-2023/project-l04gr05/tree/main/ForwardWarfare/src/main/java/com/ldts/ForwardWarfare/Element/Facility)

- **Consequences:** those classes become responsible for the management of the facility, instead of having to store which facility is related to which tile.

 ### The game has a lot of phases

- **Problem in Context:** a game like ours need to be able to have all the information of each player and the map in the same place, in order to be able to handle a player action.

- **The Pattern:** we decided to use the state pattern and the game loop pattern. This is due to the game having a set number of possible states and the necessity of determining the next state the game is gonna be in after each user input.

- **Implementation:** represented in Red (State) / Purple (Game Loop) in the UML but no code implementation yet.

- **Consequences:** these pattern are useful as they allows us to have all the necessary map, player, troop and facility information in the same place. Furthermore, the state class becomes the brain of the game, managing everything that happens and allowing us to have a central loop (game loop pattern) that controls the game and read the user input.

## Known-code smells

### \*\*MISSING\*\* - error prone warnings + develop smells

## Testing

### \*\*MISSING PHOTO + MISSING REPORT\*\*

### Screenshot of coverage report
<p align="center" justify="center">
  <img src="images/screenshots/codeCoverage"/>
</p>
<p align="center">
  <b><i>Fig X. Code coverage screenshot</i></b>
</p>

### Link to mutation testing report
[Mutation tests](https://www.google.com)

## Self-evaluation

### \*\*MISSING\*\*

Felipe Esteves: **X%**
Gabriel Lima: **X%**
Renata Simões: **X%**
