package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/** Created by Mateusz on 2016-06-11. */

public class User implements Player {

    final Board board;
    final ShipsFactory shipsFactory;
    Player opponent;
    final int[] shootCoordinates;
    boolean hit;

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
    public Map<Ship, Integer[][]> fillBoard(){
        for (int i=5; i>0; i--){
            while(shipsFactory.remaining(i) > 0){
                Ship ship = shipsFactory.make(i);
                int x = (int) (Math.random() * board.getSize());
                int y = (int) (Math.random() * board.getSize());
                boolean horizontally = Math.random() > 0.5;
                if (board.placeShip(ship, x, y, horizontally)){
                    shipsFactory.wasPlaced(i);
                }
            }
        }
        return board.getAllShipsPosition();
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
    public boolean wasHit(){
        return hit;
    }

    @Override
    public boolean shootTo(int x, int y){
        hit = opponent.takeShoot(x, y);
        shootCoordinates[0] = x;
        shootCoordinates[1] = y;
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
