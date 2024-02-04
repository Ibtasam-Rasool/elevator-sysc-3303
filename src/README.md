
# SYSC3303 Elevator 
___
This repository is for the SYSC3303 Elevator Group Project.

## Table of Contents
___
- Description
- Getting started
  - Prerequisites
  - Setup
  - Usage
- Credits

## Description
___
The project is composed of an elevator control system and simulator. The system consists of an
elevator controller (the Scheduler), a simulator for the elevator cars (which includes, the lights, buttons, doors
and motors) and a simulator for the floors. The elevator controller is multi-threaded, and the simulation is configurable in terms of the number of floors,
the number number of elevators, the time it takes to open and close the doors, and the time it takes to move
between floors. These values are stored in a CSV file located in src.

## Geting Started
___
### Prerequisites
Ensure that you have Java installed on your system.
The test classes for this project use JUnit 5.
JUnit 5 requires Java 8 (or higher) at runtime. 

### Setup
Add tasks to the csv file with the name "ElevatorCallSheet - Sheet1.csv" in the src folder with the following format:

    Time,Floor,Floor Button,Car Button
    hh:mm:ss.mmm,n,Up/Down,n

    Followed by the values in rows below it ordered by time
    Example CSV file:

    Time,Floor,Floor Button,Car Button
    hh:mm:ss.mmm,n,Up/Down,n
    14:05:15,2,Up,4
    15:06:05,4,Down,1
    19:00:00,1,Up,2
    22:25:55,3,Down,2

### Usage
First, open your terminal and go to the src directory. Then do the following:
1. Compile the Java program:
```javac Main.java```
2. Run the program:
```java Main```

## Credits
___

- Jestan Brar

- Daniel Godfrey

- Ibtasam Rasool

- Saad Sheikh

- Quinton Tracey 


