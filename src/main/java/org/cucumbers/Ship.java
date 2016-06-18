package org.cucumbers;

/** Created by Mateusz on 2016-06-11. */

public class Ship {
    private int size;
    private int shotParts = 0;

    public Ship(int size){
        this.size = size;
    }

    boolean isAlive() {
        return (size - shotParts) > 0;
    }

    public int getSize() {
        return size;
    }

    public void damage() {
        this.shotParts = this.shotParts + 1;
    }
}
