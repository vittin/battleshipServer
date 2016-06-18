package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Created by Mateusz on 2016-06-11. */

class Cpu extends User implements ComputerPlayer {

    private Random generator  = new Random();
    private boolean killMode = false;
    private int originalX;
    private int originalY;
    private int nextX;
    private int nextY;
    private boolean aimedShot=false;
    private Directions shootDirections=null;
    private List<Directions> directions = new ArrayList<Directions>(){{
        add(Directions.LEFT);
        add(Directions.RIGHT);
        add(Directions.UP);
        add(Directions.DOWN);
    }} ;
    private int possibilitiesLeft = directions.size();
    Cpu(Board board, ShipsFactory shipsFactory) {
        super(board, shipsFactory);
    }

    @Override
    public void generateShoot() {
        if (killMode) {
           killMode();
        } else {
          randomShoot();
        }
    }

    private void  randomShoot() {
        try {
            int x = generator.nextInt(board.getSize());
            int y = generator.nextInt(board.getSize());
            if (this.shootTo(x, y)) {
                killMode = true;
                originalX = x;
                originalY = y;
            }

        } catch (RuntimeException e ){
            randomShoot();
        }

    }

    private void killMode(){

        if (targetIsAlive(originalX, originalY)) {

            if (!aimedShot) {
                shootDirections = directions.get(generator.nextInt(possibilitiesLeft));
                nextX = originalX;
                nextY = originalY;
            }

            switch (shootDirections) {
                case LEFT:
                    nextX = nextX - 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Directions.UP,Directions.DOWN);
                    }
                    else {
                        badGuess(Directions.LEFT);
                    }
                    break;
                case RIGHT:
                    nextX += 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Directions.UP,Directions.DOWN);
                    }
                    else {
                        badGuess(Directions.RIGHT);
                    }
                    break;
                case UP:
                    nextY += 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Directions.LEFT,Directions.RIGHT);
                    }
                    else {
                        badGuess(Directions.UP);
                    }
                    break;
                case DOWN:
                    nextY += 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Directions.LEFT,Directions.RIGHT);
                    }
                    else {
                        badGuess(Directions.DOWN);
                    }
                    break;
            }
        }

        else {
            endKillMode();}
    }

    private void goodGuess(Directions direction1, Directions direction2){
        aimedShot=true;
        directions.remove(direction1);
        directions.remove(direction2);
        possibilitiesLeft -= 2;
    }

    private void badGuess(Directions direction){
        aimedShot=false;
        possibilitiesLeft -=1;
        directions.remove(direction);

    }

    private void endKillMode(){
        killMode=false;
        directions.clear();
        directions.add(Directions.LEFT);
        directions.add(Directions.RIGHT);
        directions.add(Directions.UP);
        directions.add(Directions.DOWN);
        possibilitiesLeft = directions.size();
        generateShoot();
    }


    @Override
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
    @Autowired
    @Qualifier("player")
    public void setOpponent(Player opponent){
        super.setOpponent(opponent);
    }

    @Override
    public boolean isHuman(){
        return false;
    }
}
