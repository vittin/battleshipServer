package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Created by Mateusz on 2016-06-11. */

public class Cpu extends ComputerPlayer {

    private final Board board;
    private final ShipsFactory shipsFactory;
    private final int[] shootCoordinates;
    private Player opponent;
    private Ship lastHitShip;
    private boolean hit = false;
    private boolean continueShooting = false;
    private boolean horizontalIsGoodChoice;



    @Autowired
    public Cpu(Board board, ShipsFactory shipsFactory){
        this.board = board;
        this.shipsFactory = shipsFactory;
        shootCoordinates = new int[2];
    }

    public void fillBoard(){
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
    }

    @Override
    public int[] getShootCoordinates(){
        return this.shootCoordinates;
    }

    @Autowired
    @Qualifier("player")
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    @Override
    public boolean isHit(){
        return this.hit;
    }

    @Override
    public boolean shootTo(int x, int y){
        shootCoordinates[0] = x;
        shootCoordinates[1] = y;
        hit = opponent.takeShoot(x, y);
        return hit;
    }

    public void generateShoot(){
        int x, y;
        try {
           if(false){
            } else {
                x = (int) (Math.random() * board.getSize());
                y = (int) (Math.random() * board.getSize());
                shootTo(x, y);
            }
        } catch (RuntimeException e){
            this.generateShoot();
            return;
        }

        if (lastHitShip != null && !lastHitShip.isAlive()){
            continueShooting = false;
        }
        this.shootCoordinates[0] = x;
        this.shootCoordinates[1] = y;
    }

    @Override
    public boolean takeShoot(int x, int y) {
        boolean shoot = board.shoot(x, y);
        if (shoot) {
            opponent.setLastHitShip(board.getField(x, y).getShip());
        }

        return shoot;
    }

    @Override
    public void setLastHitShip(Ship lastHitShip){
        this.lastHitShip = lastHitShip;
    }

    @Override
    public boolean isShipFinished(){
        return !lastHitShip.isAlive();
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

    @Override
    public boolean isEndGame(){
        return board.remainingShips() == 0;
    }

    @Override
    public boolean isHuman(){
        return false;
    }

}
