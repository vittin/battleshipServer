package org.cucumbers;

/** Created by Mateusz on 2016-06-11. */

public interface Player {

    int remainingShips(int shipSize);

    boolean placeShip(int x, int y, int size, boolean horizontally);

    boolean canStartGame();

    //shoot to opponent -> he tell you did you hit him. (calling takeShoot() method).
    boolean shootTo(int x, int y);

    //opponent shoot to you -> tell him if he hit you (called by shootTo() method).
    boolean takeShoot(int x, int y);

    //opponent shot to player -> here are coordinates;
    int[] getShootCoordinates();

    boolean targetIsAlive(int x, int y);

    boolean isHit();

    boolean isEndGame();

    boolean isHuman();

    boolean canShootHere(int x, int y);
}
