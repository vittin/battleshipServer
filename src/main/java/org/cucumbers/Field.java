package org.cucumbers;

/**
 * Created by Mateusz on 2016-06-11.
 */

public class Field {

    private boolean empty = true;
    private Ship ship;
    private boolean shooted = false;

    public boolean hit() throws RuntimeException {
        if (!shooted){
            shooted = true;
            return !empty;
        } else {
            throw new RuntimeException("field has already been hit");
        }
    }

    public void placeShip(Ship ship) {
        empty = false;
        this.ship = ship;
    }

    public boolean isEmpty() {
        return empty;
    }
}
