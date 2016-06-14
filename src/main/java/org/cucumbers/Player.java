package org.cucumbers;

/** Created by Mateusz on 2016-06-11. */

public interface Player {

    int remainingShips(int shipSize);

    boolean placeShip(int x, int y, int size, boolean horizontally);

    boolean canStartGame();

    boolean shoot(int x, int y);

}
