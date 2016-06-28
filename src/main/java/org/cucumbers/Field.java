package org.cucumbers;

/** Created by Mateusz on 2016-06-11. */

class Field {

    private boolean empty = true;
    private Ship ship;

    private boolean shot = false;

    boolean hit() throws RuntimeException {
        if (!this.shot){
            this.shot = true;
            boolean hasShip = this.hasShip();

            if(hasShip) {
                ship.damage();
            }

            return hasShip;
        } else {
            throw new RuntimeException("field has already been hit");
        }
    }

    void placeShip(Ship ship) {
        empty = false;
        this.ship = ship;
    }

    void lock() {
        this.empty = false;
    }

    Ship getShip(){
        return this.ship;
    }

    boolean isEmpty() {
        return empty;
    }

    boolean wasShot() {
        return shot;
    }

    boolean hasShip() {
        return (this.ship != null);
    }
}
