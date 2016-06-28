package org.cucumbers;

/** Created by Mateusz on 2016-06-11. */

class Ship {
    private final int size;
    private int shotParts = 0;

    Ship(int size){
        this.size = size;
    }

    boolean isAlive() {

        return (size - shotParts) > 0;
    }

    int getSize() {
        return size;
    }

    void damage() {
        this.shotParts = this.shotParts + 1;
    }
}
