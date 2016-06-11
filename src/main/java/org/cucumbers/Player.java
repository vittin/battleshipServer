package org.cucumbers;

/**
 * Created by Mateusz on 2016-06-11.
 */

public interface Player {
    boolean shoot(int x, int y);
    boolean placeShip(int x, int y, int size, boolean horizontally);
}
