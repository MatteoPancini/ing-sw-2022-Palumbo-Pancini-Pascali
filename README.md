# ERIANTYS - Software Engineering Final Project 2021/2022
The project consists of a Java implementation of the board game Eriantys, made by Cranio Creations.

![This is an image](src/main/resources/graphics/eriantys_banner.png)






## 👤 GroupID: GC31
+ Francesco Palumbo
+ Matteo Pancini
+ Luigi Pascali
  
**Teacher**: Gianpaolo Cugola

## ⁉️ Implemented Functionalities
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


## ⚙️ Test cases coverage
**Coverage criteria: Code Lines**

All classes (from both Model and Controller's Packages) have 100% of class coverage.

| Package    | Tested Class       |   Coverage    |
|:-----------|:-------------------|:-------------:|
| Controller | Controller         | 146/154 (94%) |
| Controller | TurnController     | 317/389 (81%) |
| Controller | ExpertController   | 131/145 (90%) |
| Model      | Model Package      | 496/533 (94%) |

## 🏃‍♂️ Run jar information
In order to play the game file, download the JAR file from "deliverables" folder.
Then run it from **terminal** (from the directory where you save it) by typing:
>*java -jar softeng-GC31.jar*

For the best game experience we suggest:
+ CLI:
  + set UTF-8 compatibility on your terminal
  + set ANSI compatibility on your terminal
+ GUI:
  + if your computer has small memory size or "Load Exception - Java Heap Size" occurs, run the JAR file by typing:
    >*java -Xmx4096m -jar softeng-GC31.jar*
  + set OS screen size to 100% in order to enjoy the complete scene board

## 🔨 Tools and Languages
+ Programming Language: ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
+ Project Management: ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
+ IDE: ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
+ Testing and Bug Fixing: Sonarqube
+ UML and Documentation: draw.io
