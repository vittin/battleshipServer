package org.cucumbers;

/** Created by Mateusz on 2016-06-11. */

public class Field {

    private boolean empty = true;
    private Ship ship;
    private boolean shot = false;

    public boolean hit() throws RuntimeException {
        if (!this.shot){
            this.shot = true;

            if(!empty) {
                ship.damage();
            }

            return !empty;
        } else {
            throw new RuntimeException("field has already been hit");
        }
    }

    void placeShip(Ship ship) {
        empty = false;
        this.ship = ship;
    }

    public Ship getShip(){
        return this.ship;
    }

    public boolean isEmpty() {
        return empty;
    }
}
