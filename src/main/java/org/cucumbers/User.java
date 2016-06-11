package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mateusz on 2016-06-11.
 */

public class User implements Player {

    private final Board board;

    @Autowired
    public User(Board board) {
        this.board = board;
    }

    @Override
    public boolean shoot(int x, int y){
        return board.shoot(x, y);
    }

    @Override
    public boolean placeShip(int x, int y, int size, boolean horizontally){
        return this.board.placeShip(x, y, size, horizontally);
    }
}
