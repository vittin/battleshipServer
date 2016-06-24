package org.cucumbers;

import java.util.*;

/** Created by Mateusz on 2016-06-11. */

public class Board {

    private final List<Field> fields;
    private final Map<Ship, Integer[][]> ships;
    private int size;

    public Board(int size) {
        this.size = size;
        this.fields = new ArrayList<>(size*size);
        this.ships = new HashMap<>();
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
        int shipSize = ship.getSize();
        if (horizontally){checkCoordinate = x;} else {checkCoordinate = y;}
        if (checkCoordinate + shipSize <= this.size){
            List<Field> fields = new ArrayList<>();
            List<Field> notAvailable = new ArrayList<>();
            Integer position[][] = new Integer[shipSize][2];
            for (int i = 0; i < shipSize; i++){
                if (checkCoordinate == x) {
                    Field field = getField(x+i,y);
                    if(field.isEmpty()){
                        fields.add(field);
                        notAvailable.addAll(getFieldNeighbours(x, y));
                    } else {
                        return false;
                    }

                } else {
                    Field field = getField(x, y+i);
                    if(field.isEmpty()){
                        fields.add(field);
                        notAvailable.addAll(getFieldNeighbours(x, y));
                    } else {
                        return false;
                    }
                }
            }

            fields.forEach(field -> field.placeShip(ship));
            ships.put(ship, position);
            notAvailable.forEach(Field::lock);
            return true;
        }
        return false;
    }

    private List getFieldNeighbours(int x, int y) {
        ArrayList<Field> neighbours = new ArrayList<>();
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

    public Map<Ship,Integer[][]> getAllShipsPosition() {
        return ships;
    }

    public boolean shoot(int x, int y) {

        return getField(x, y).hit();
    }

    int remainingShips(){
        return ships.size();
    }

}
