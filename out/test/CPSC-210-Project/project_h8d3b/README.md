# Battleship CPSC 210 Project 


### What will the Application do? ###
The application will be a version of the **battleship** board game. This will allow two different users to alternate turns at setting up their own ship arrangements \
with the same computer. From here both players can take turns launching missiles at the other player's arrangement until all ships are sunk, and finally determining the winner.

### Who will use it? ###
Can be played by two players who are required to swap the computer for their turn

### Why this project interests me ###
I'm interested in working on this project specifically as I really enjoyed playing battleship when I was younger. I also think it'll be interesting to implement a two 
player game in such a way to make the gameplay *fair* and *smooth*. 

## User Stories
* As a user, I want to add battleships to my grid
* As a user, I want to keep track of where my missiles have landed and which ones hit a ship or didn't
* As a user, I want to know when I sunk an opposing team's battleship
* As a user, I want to be notified when someone wins the match
* As a user, I want to keep track of *how many* ships have sunk (score)
* As a user, I want to be able to save the progress of the game after the ship placement phase

## Phase 4: Task 2
Thu Nov 25 01:36:17 PST 2021
Changed turn

Thu Nov 25 01:36:19 PST 2021
Changed turn

Thu Nov 25 01:36:22 PST 2021
Ship Placed at 0,1

Thu Nov 25 01:36:22 PST 2021
Ship Placed at 2,2

Thu Nov 25 01:36:23 PST 2021
Ship Placed at 2,3

Thu Nov 25 01:36:24 PST 2021
Ship Placed at 1,4

Thu Nov 25 01:36:24 PST 2021
Ship Placed at 0,5

Thu Nov 25 01:36:24 PST 2021
Changed turn

Thu Nov 25 01:36:24 PST 2021
Ship Placed at 2,5

Thu Nov 25 01:36:25 PST 2021
Ship Placed at 2,4

Thu Nov 25 01:36:25 PST 2021
Ship Placed at 1,3

Thu Nov 25 01:36:25 PST 2021
Ship Placed at 0,3

Thu Nov 25 01:36:26 PST 2021
Ship Placed at 0,4

Thu Nov 25 01:36:26 PST 2021
Changed turn

Thu Nov 25 01:36:27 PST 2021
Hit placed at 2,4

Thu Nov 25 01:36:27 PST 2021
Hit placed at 2,4

Thu Nov 25 01:36:29 PST 2021
Changed turn

Thu Nov 25 01:36:29 PST 2021
Miss placed at 4,5

Thu Nov 25 01:36:30 PST 2021
Changed turn

Thu Nov 25 01:36:31 PST 2021
Miss placed at 2,2

Thu Nov 25 01:36:31 PST 2021
Changed turn

Thu Nov 25 01:36:32 PST 2021
Hit placed at 2,3

Thu Nov 25 01:36:32 PST 2021
Hit placed at 2,3

Thu Nov 25 01:36:33 PST 2021
Changed turn

Saved game to ./data/battleship.json

Process finished with exit code 0

## Phase 4: Task 3
If I had more time to work on this project, I would definitely spend more time refactoring the GUI. 
Before starting the GUI, I didn't really have a precise plan of how I would implement each stage of my GUI. 
As a result, I encountered new challenges at each stage of my GUI (i.e changing JPanels for entering names, ship placement, attacking, etc.).
This forced me to constantly make adjustments to several of my classes as I worked through phase 4. The BattleShipAttacking class was
especially difficult to implement as I needed to update the GUI after every turn and a lot of the code related to switching turns. 
This class became quite extensive with almost 300 lines of code, which could've been refactored into separate classes that were each responsible for things
like displaying the score, displaying the ocean and target grids, and controlling the logic of the game. 

