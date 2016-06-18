package org.cucumbers;

import java.util.ArrayList;
import java.util.List;

/** Created by Mateusz on 2016-06-11. */

public class Board {

    private final List<Field> fields;
    private final List<Ship> ships;
    private int size;

    public Board(int size) {
        this.size = size;
        this.fields = new ArrayList<>(size*size);
        this.ships = new ArrayList<>();
        for (int i = 0; i < size*size; i++){
            fields.add(new Field());
        }
    }

    public int getSize() {
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

    public boolean placeShip(Ship ship, int x, int y, boolean horizontally){
        int checkCoordinate;
        if (horizontally){checkCoordinate = x;} else {checkCoordinate = y;}
        if (checkCoordinate + ship.getSize() <= this.size){
            List<Field> fields = new ArrayList<>();
            for (int i = 0; i < ship.getSize(); i++){
                if (checkCoordinate == x) {
                    Field field = getField(x+i,y);
                    if(field.isEmpty()){
                        fields.add(field);
                    } else {
                        return false;
                    }

                } else {
                    Field field = getField(x, y+i);
                    if(field.isEmpty()){
                        fields.add(field);
                    } else {
                        return false;
                    }
                }
            }

            fields.forEach(field -> field.placeShip(ship));
            ships.add(ship);
            return true;
        }
        return false;
    }

    public void removeShip(Ship ship){
        int index = ships.indexOf(ship);
        ships.remove(index);
    }

    public boolean shoot(int x, int y) {

        return getField(x, y).hit();
    }

    int remainingShips(){
        return ships.size();
    }
}
