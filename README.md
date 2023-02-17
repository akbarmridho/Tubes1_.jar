# IF2211_Strategi Algoritma
## *Tubes1_13521093_13521116_13521134*


## **Table of Contents**
* [Program Description](#program-description)
* [Required Program](#required-program)
* [How to Run The Program](#how-to-run-the-program)
* [Progress Report](#progress-report)
* [Authors and Workload Distribution](#authors-and-workload-distribution)
* [Folders and Files Description](#folders-and-files-description)

## **Program Description**
*Galaxio* is a *battle-royale* competition game of several spaceships. Every player will be representated by a spaceship with a goal to be the last survivor of the game. Bot used in the game will be constructed using *greedy algorithm*. The spaceships (bots) used in the game are allowed to use various strategy to survive, including shooting torpedos to other ships, deploying shield and using wormhole to retreat, and colliding itself to other ships or food to increase its size. The bot inside this repository is developed using Java programming language.

## **Required Program**
Here are the things you need to prepare to run the program: 
| No. | Required Programs | Reference Link | 
|----| -----------------| ----------------|
| 1 | Galaxio *starter-pack* |  [Galaxio starter-pack](https://github.com/EntelectChallenge/2021-Galaxio/releases/tag/2021.3.2) |
| 2 | Java (Java 11 or later) | [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) | 
| 3 | Nodejs | [Nodejs](https://nodejs.org/en/download/)|
| 4 | .Net Core 3.1 | [.Net Core 3.1](https://dotnet.microsoft.com/en-us/download/dotnet/3.1) |
| 5 | .Net Core 5.0 | [.Net Core 5.0](https://dotnet.microsoft.com/en-us/download/dotnet/5.0) |

 
## **How to Run The Program**

### Preparing the Program
1. Make sure that the required programs have been installed <br>
2. Download and extract the game *starter-pack* <br>
3. Clone this repository <br>
    Preferably inside the extracted folder <br>
    ```sh
    $ cd starter-pack/starter-bots
    ```
    ```sh
    $ git clone https://github.com/akbarmridho/Tubes1_.jar.git
    ```

### Starting the Program
Every code displayed in this instruction assuming that the user runs the program with the *starter-pack* folder as the current directory in the terminal.
1. Configurate the used bot count in the game you want to start. To change the settings, change the bot count variable inside `appsettings.json` inside the *runner-publish* and *engine-publish* folders.
2. Inside the starter-pack folder, open a new terminal and run `GameRunner.dll`
    ```sh
    $ cd runner-publish
    $ dotnet GameRunner.dll
    ```
3. Inside the starter-pack folder, open a new terminal and run `Engine.dll`
    ```sh
    $ cd enginer-publish
    $ dotnet Engine.dll
    ```
4. Inside the starter-pack folder, open a new terminal and run `logger-publish`
    ```sh
    $ cd logger-publish
    $ dotnet Logger.dll
    ```
5. Open new terminals **as much as the bot count** in the settings. <br>
    To run the reference bot, you can use the command:
    ```sh
    $ cd reference-bot-publish
    $ dotnet ReferenceBot.dll
    ``` 
    To run the bot in this repository, try:
    ```sh
    # java -jar <path to the .jar file of this repo>
    # if you follow the instructions above, the .jar file should be inside the starter-bots folder
    $ java -jar starter-bots/Tubes1_.jar/target/JavaBot.jar
    ```
6. Extract the visualiser files inside the *visualiser* folder
7. Open the extracted folder and run *Galaxio.exe*


## **Progress Report**

| Strategy |Point | Yes | No |
|--|---|-----|------|
| Attacking | Torpedos Deployment           | &check; |  |
|           | Closest Enemy Targeting       | &check; |  |
|           | Angular Shooting Adjustment   | &check; |  |
|           | Supernova Usage               |  | &check; |
| Defending | Evading Torpedos and Teleporter | &check; |  |
|           | Evading Supernova             | &check; |  |
|           | Deploying Shield              | &check; |  |
|           | Runaway using Wormhole        |  | &check; |
| Exploring | Shortest Food Path Finding    | &check; |  |
|           | Small Gas Cloud Shooting      | &check; |  |
|           | 2 Same Distance Food Decision |  | &check; |
|           | Going Outside of the Map Prevention | &check; |  |
| Others    | Radar                         | &check; |  |
|           | Late Game Strategy            |  | &check; |


## **Authors and Workload Distribution** 
| Name | ID | Workload |
|-----|----|----------|
| Akbar Maulana Ridho | 13521093 | Attacking Algorithm|
| Juan Christopher Santoso | 13521116 | Exploring Algorithm |
| Rinaldy Adin | 13521134 | Defending Algorithm |


## **Folders and Files Description**
    .
    ├─ .github                 
    ├─ src                              # Contains every algorithm in constructing the bot
        └─ main
            └─ java
                ├─ Actions
                    ├─ Armory.java
                    ├─ SearchEnemy.java
                    ├─ SearchFood.java
                    ├─ SearchSupernova.java
                    └─ SearchTorpedoes.java
                ├─ Agents
                    ├─ Ling                             # Used Bot
                        ├─ Strategy
                            ├─ Attacker.java
                            ├─ Defender.java
                            ├─ Explorer.java
                            ├─ EscapeGas.java
                            ├─ EvadeCollision.java
                            ├─ Shield.java
                            ├─ Sniper.java
                            ├─ Supernova.java
                            └─ ToCenter.java
                        ├─ Ling.java
                        ├─ Priority.java
                        └─ StrategyInterface.java
                    ├─ Agent.java
                    └─ AgentManager.java
                ├─ Enums
                    ├─ ObjectTypes.java   
                    └─ PlayerActions.java
                ├─ Models
                    ├─ GameObject.java
                    ├─ GameState.java
                    ├─ GameStateDto.java
                    ├─ PlayerAction.java
                    ├─ Position.java
                    └─ World.java
                ├─ Services
                    ├─ DebugWriter.java
                    ├─ GameWatcher.java
                    ├─ GameWatcherManager.java
                    ├─ Radar.java
                    ├─ RadarSection.java
                    └─ RadarUnitArea.java
                ├─ Utils
                    └─ Math.java
                └─ Main.java
    ├─ target                           # Contains compiled program 
        ├─ target
            ├─ classes                  # Contains compiled classes files
                └─ ...
            ├─ generated-sources
                └─ ...
            ├─ libs
                └─ ...
            ├─ maven-archiver
                └─ ...
            ├─ maven-status
                └─ ...
            └─ JavaBot.jar              # Compiled program in .jar
    ├─ doc                              # Contains documentation files 
    ├─ Dockerfile  
    ├─ pom.xml                  
    ├─ test.bat                         # Used to support user when running the program
    └─ README.md







