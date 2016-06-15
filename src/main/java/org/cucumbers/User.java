package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Created by Mateusz on 2016-06-11. */

public class User implements Player {

    private final Board board;
    private final ShipsFactory shipsFactory;
    private final int[] shootCoordinates;
    private Player opponent;
    private Ship lastHitShip;  //my last good shot;
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
    public boolean isHit(){
        return hit;
    }

    @Override
    public boolean shootTo(int x, int y){
        shootCoordinates[0] = x;
        shootCoordinates[1] = y;
        hit = opponent.takeShoot(x, y);
        return hit;
    }

    @Override
    public boolean takeShoot(int x, int y){
        boolean shoot = board.shoot(x,y);
        if(shoot){
            opponent.setLastHitShip(board.getField(x,y).getShip());
        }
        return shoot;
    }

    @Override
    public void setLastHitShip(Ship lastHitShip){
        this.lastHitShip = lastHitShip;
    }

    @Override
    public boolean isShipFinished() {
        return lastHitShip != null && !lastHitShip.isAlive();
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
