package org.cucumbers;

/**
 * Created by Mateusz on 2016-06-11.
 */

public class Ship {
    private int size;
    private int shootedParts = 0;

    public Ship(int size){
        this.size = size;
    }

    boolean isAlive() {
        return size - shootedParts > 0;
    }

    public int getSize() {
        return size;
    }
}
