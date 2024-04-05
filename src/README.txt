How to execute:
    make
    ./Agent <MAX NUMBER OF PLAYERS> <NUMBER OF POINTS TO WIN> <GREEN EXTENSION FILE NAME> <RED EXTENSION FILE NAME>

File Contents:
    README.txt
    makefile
    Agent.java
    Card.java
    GreenCard.java
    RedCard.java
    Hand.java
    Players.java
    manifest.txt

Descriptions:
    README.txt - This file contains descriptions of all files in this folder 
                 and their usages

    makefile  -  Creates jar file
                 Use by calling:
                        make
                    or
                        make all

                 Clean Directory
                        make clean

    manifest.txt - designates main java file

    Card.java - The Card class serves as a Java interface that is extended
                by both the GreenCard and RedCard classes.

    RedCard.java & GreenCard.java - The GreenCard and RedCard classes were used
                to model the Green and Red apple cards, storing relevant information
                about these cards and ease of utilization.

    Agent.java - Core client program for managing the Apples to Apples agent,
                initializing game parameters and creating card decks based on specified files.
                It enters a user interface loop, serving as both player and judge by selecting
                cards and determining winners according to specified guidelines.

    Players.java - Tracks player scores and determines win conditions, adapting the agent's
                strategy as a judge to that of a contrarian when a player is close to winning.
                It also records judge behavior to influence the agent's player behavior,
                aiming for optimized gameplay.

    Hand.java - The Hand class, employed by the agent, holds the current round's green and
                red apple cards. This class is responsible for implementing the favourability
                scoring system, allowing the agent to select the best card based on its assigned
                behaviour, either as best matching or as a contrarian.

    BasicGreenCards.txt / BasicRedCards.txt - Base set of Cards used in database

    SampleGreenExtension.txt / SampleRedExtension.txt - Example extension Files
