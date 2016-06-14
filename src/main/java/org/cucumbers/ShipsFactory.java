package org.cucumbers;

/** Created by Mateusz on 2016-06-13. */

public class ShipsFactory {

    private int[] ships = new int[5];

    ShipsFactory(int s1, int s2, int s3, int s4, int s5){
        ships[0] = s1;
        ships[1] = s2;
        ships[2] = s3;
        ships[3] = s4;
        ships[4] = s5;
    }

    public int remaining(int shipSize){
        return ships[shipSize-1];
    }

    public Ship make(int shipSize) throws RuntimeException {
        if(this.remaining(shipSize) < 1){
            throw new RuntimeException("This ship is not available.");
        }
        return new Ship(shipSize);
    }

    public void wasPlaced(int shipSize) {
        ships[shipSize - 1] -= 1;
    }

    public boolean isFinished() {
        for (int availableShip : ships){
            if (availableShip != 0){
                return false;
            }
        }
        return true;
    }
}
