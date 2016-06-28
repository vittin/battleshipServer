package org.cucumbers;

import java.util.ArrayList;
import java.util.List;

/** Created by Mateusz on 2016-06-11. */

public class Board {

    private final List<Field> fields;
    private final List<Ship> ships;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.fields = new ArrayList<>(size*size);
        this.ships = new ArrayList<>();
        for (int i = 0; i < size*size; i++){
            fields.add(new Field());
        }
    }

    int getSize() {
        return size;
    }

    Field getField(int x, int y) throws RuntimeException{
        int index = y*size + x;
        if (x < this.size && y < this.size){
            return fields.get(index);
        } else {
            throw new RuntimeException("Out of bounds");
        }
    }

    boolean placeShip(Ship ship, int x, int y, boolean horizontally){

        Integer checkCoordinate;
        int shipSize = ship.getSize();
        if (horizontally){checkCoordinate = x;} else {checkCoordinate = y;}
        if (checkCoordinate + shipSize <= this.size){
            List<Field> fields = new ArrayList<>();
            List<Field> notAvailable = new ArrayList<>();

            for (int i = 0; i < shipSize; i++){

                Field field = getField(x,y);
                if(field.isEmpty()){
                    fields.add(field);
                    notAvailable.addAll(getFieldNeighbours(x, y));
                    if (horizontally){x++;} else {y++;}
                } else {
                    return false;
                }
            }

            fields.forEach(field -> field.placeShip(ship));
            ships.add(ship);
            notAvailable.forEach(Field::lock);
            return true;
        }
        return false;
    }

    private List<Field> getFieldNeighbours(int x, int y) {
        List<Field> neighbours = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i != 0 || j != 0) {
                    try {
                        neighbours.add(getField(x + i, y + j));
                    } catch (Exception ignored){}
                }
            }
        }

        return neighbours;
    }

    boolean shoot(int x, int y) {

        return getField(x, y).hit();
    }

    int remainingShips(){
        int remaining = 0;
        for (Ship ship: ships) {
            if (ship.isAlive()){
                remaining +=1;
            }
        }

        return remaining;
    }

}
