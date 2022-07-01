# ERYANTIS - Software Engineering Final Project 2021/2022
The project consists of a Java implementation of the board game Eryantis, made by Cranio Creations.

![This is an image](src/main/resources/graphics/eriantys_banner.png)






## GroupID: GC31
+ Francesco Palumbo
+ Matteo Pancini
+ Luigi Pascali
  
**Teacher**: Gianpaolo Cugola

## Implemented Functionalities
| Functionality                | Implementation |
|:-----------------------------|:--------------:|
| Basic rules                  |       ✅        |
| Complete rules               |       ✅        |
| Socket                       |       ✅        |
| CLI                          |       ✅        |
| GUI                          |       ✅        |
| Multiple games               |       ✅        |
| 4 players game mode          |       ✅        |
| 12 characters implementation |       ✅        |
| Persistence                  |       ⛔        |
| Disconnection resilience     |       ⛔        |


## Test cases coverage
**Coverage criteria: Code Lines**

All classes (from both Model and Controller's Packages) have 100% of class coverage.

| Package    | Tested Class       |    Coverage    |
|:-----------|:-------------------|:--------------:|
| Controller | Controller         | 174/179 (97%) |
| Controller | TurnController     | 346/419 (82%) |
| Controller | ExpertController   | 129/142 (90%) |
| Model      | Model Package      | 517/553 (93%) |

## Run game instructions
In order to run the game, download the JAR file contained in the "deliverables" folder of the repository.
Then run it from **terminal** (from the directory where the JAR is stored) by typing:
>*java -jar softeng-GC31.jar*

**Remember**: you need to have JAVA installed in your PC!

For the best possible experience we suggest to:
+ CLI:
  + set UTF-8 compatibility on your terminal
  + set ANSI compatibility on your terminal
+ GUI:
  + if your PC has small memory or your GUI setup fails with "load exception", please run the jar typing:
    >*java -Xmx4096m -jar softeng-GC31.jar*
