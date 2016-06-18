package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Created by Mateusz on 2016-06-11. */

public class User implements Player {

    final Board board;
    final ShipsFactory shipsFactory;
    private final int[] shootCoordinates;
    Player opponent;
    private boolean hit;

    @Autowired
    public User(Board board, ShipsFactory shipsFactory) {
        this.board = board;
        this.shipsFactory = shipsFactory;
        this.shootCoordinates = new int[2];
    }

    @Autowired
    @Qualifier("cpu")
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    @Override
    public int[] getShootCoordinates() {
        return shootCoordinates;
    }

    @Override
    public boolean canShootHere(int x, int y){
        return !board.getField(x, y).wasShot();
    };

    @Override
    public boolean isHit(){
        return hit;
    }

    @Override
    public boolean shootTo(int x, int y){
        hit = opponent.takeShoot(x, y);
        return hit;
    }

    @Override
    public boolean takeShoot(int x, int y){
        return board.shoot(x,y);
    }

    @Override
    public boolean targetIsAlive(int x, int y){
        return board.getField(x, y).hasShip() && board.getField(x, y).getShip().isAlive();
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
            return false;
        }
        try {
            Ship ship = this.shipsFactory.make(size);
            boolean response = this.board.placeShip(ship, x, y, horizontally);
            if (response){
                shipsFactory.wasPlaced(size);
            }
            return response;

        } catch(Exception e){

            return false;
        }
    }

    @Override
    public boolean isEndGame(){
        return board.remainingShips() == 0;
    }

    @Override
    public boolean isHuman(){
        return true;
    }

}
