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
*Galaxio* is a *battle-royale* competition game of several spaceships. Every player will be representated by a spaceship with a goal to be the last survivor of the game. Bot used in the game will be constructed using *greedy algorithm*.

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

1. Clone this repository <br>
```sh
$ git clone  https://github.com/akbarmridho/Tubes1_.jar.git
```



## **Progress Report**

| Point | Yes | No |
|-----|-----|------|
|The program is able to be compiled without any issues| &check; |   |
|The program successfully starts and runs | &check;   |  |
|The program is able to read user input and to automatically generate input itself | &check;  |  |
|The program is able to display an output | &check;  |  |
|Each solution given by the program equals to 24 | &check;  |  |
|The program is able to save the solution(s) inside a text file| &check;  |  |


## **Authors and Workload Distribution** 
| Name | ID | Workload |
|-----|----|----------|
| Akbar Maulana Ridho | 13521093 | Attacking Algorithm|
| Juan Christopher Santoso | 13521116 | Exploring Algorithm |
| Rinaldy Adin | 13521134 | Defending Algorithm |


## **Folders and Files Description**
    .
    ├─ .github                 
    ├─ src                      # Contains every algorithm in constructing the bot
        └─ main
            └─ java
                ├─ Actions
                    ├─ Armory.java
                    ├─ SearchEnemy.java
                    └─ SearchFood.java
                ├─ Agents
                    ├─ Ling                             # Used Bot
                        ├─ Strategy
                            ├─ Attacker.java
                            ├─ Defender.java
                            └─ Explorer.java
                        ├─ Ling.java
                        ├─ Priority.java
                        └─ StrategyInterface.java
                    ├─ Mahamatra                        # Not Used
                    ├─ Paimon                           # Not Used
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
                    ├─ GameWatcher.java
                    ├─ GameWatcherManager.java
                    ├─ Radar.java
                    ├─ RadarSection.java
                    └─ RadarUnitArea.java
                ├─ Utils
                    └─ Math.java
                └─ Main.java
    ├─ target                   # Contains compiled program 
        ├─ target
            ├─ classes
                └─ ...
            ├─ generated-sources
            ├─ libs
            ├─ maven-archiver
            ├─ maven-status
            └─ JavaBot.jar
    ├─ doc                      # Contains documentation files 
    ├─ Dockerfile               
    ├─ pom.xml                  
    ├─ test.bat
    └─ README.md







