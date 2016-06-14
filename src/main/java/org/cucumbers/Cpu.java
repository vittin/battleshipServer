package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mateusz on 2016-06-11.
 */

public class Cpu implements Player {

    Board board;
    ShipsFactory shipsFactory;

    @Autowired
    public Cpu(Board board, ShipsFactory shipsFactory){
        this.board = board;
        this.shipsFactory = shipsFactory;
    }

    @Override
    public boolean shoot(int x, int y){
        return board.shoot(x, y);
    }

    @Override
    public boolean canStartGame(){
        return shipsFactory.isFinished();
    }

    @Override
    public int remainingShips(int shipsSize){
        return 0;
    }

    @Override
    public boolean placeShip(int x, int y, int size, boolean horizontally) {
        return false;
    }

}
