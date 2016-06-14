package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;

/** Created by Mateusz on 2016-06-11. */

public class User implements Player {

    private final Board board;
    private final ShipsFactory shipsFactory;

    @Autowired
    public User(Board board, ShipsFactory shipsFactory) {
        this.board = board;
        this.shipsFactory = shipsFactory;
    }

    @Override
    public boolean shoot(int x, int y){
        return board.shoot(x, y);
    }

    @Override
    public int remainingShips(int size){
        return shipsFactory.remaining(size);
    }

    @Override
    public boolean canStartGame(){
        return shipsFactory.isFinished();
    }

    @Override
    public boolean placeShip(int x, int y, int size, boolean horizontally){
        if (this.shipsFactory.remaining(size) == 0){
            System.out.println("remaining 0");
            return false;
        }
        try {
            Ship ship = this.shipsFactory.make(size);
            boolean response = this.board.placeShip(ship, x, y, horizontally);
            System.out.println("response: " + response);
            if (response){
                shipsFactory.wasPlaced(size);
            }
            return response;

        } catch(Exception e){
            System.out.println("Exception");
            return false;
        }
    }
}
