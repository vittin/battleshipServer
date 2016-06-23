package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Created by Mateusz on 2016-06-11. */

class CpuPszemek extends User implements ComputerPlayer {

    private Random generator  = new Random();
    private boolean killMode = false;
    private int originalX;
    private int originalY;
    private int nextX;
    private int nextY;
    private boolean aimedShot=false;
    private Direction shootDirection =null;
    private List<Direction> directions = new ArrayList<Direction>(){{
        add(Direction.LEFT);
        add(Direction.RIGHT);
        add(Direction.UP);
        add(Direction.DOWN);
    }} ;
    private int possibilitiesLeft = directions.size();
    CpuPszemek(Board board, ShipsFactory shipsFactory) {
        super(board, shipsFactory);
    }

    @Override
    public int[] generateShoot() {
        int[] result;
          result = randomShoot();

        return result;
    }

    private int[] randomShoot() {
        int x, y;
        try {
            x = generator.nextInt(board.getSize());
            y = generator.nextInt(board.getSize());

            if (!opponent.canShootHere(x, y)){
                randomShoot();
            }


            return new int[]{x, y};

        } catch (RuntimeException e ){
            randomShoot();
        }

        return null;
    }

    private void killMode(){

        if (targetIsAlive(originalX, originalY)) {

            if (!aimedShot) {
                shootDirection = directions.get(generator.nextInt(possibilitiesLeft));
                nextX = originalX;
                nextY = originalY;
            }

            switch (shootDirection) {
                case LEFT:
                    nextX = nextX - 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Direction.UP, Direction.DOWN);
                    }
                    else {
                        badGuess(Direction.LEFT);
                    }
                    break;
                case RIGHT:
                    nextX += 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Direction.UP, Direction.DOWN);
                    }
                    else {
                        badGuess(Direction.RIGHT);
                    }
                    break;
                case UP:
                    nextY += 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Direction.LEFT, Direction.RIGHT);
                    }
                    else {
                        badGuess(Direction.UP);
                    }
                    break;
                case DOWN:
                    nextY += 1;
                    if (shootTo(nextX, nextY)) {
                        goodGuess(Direction.LEFT, Direction.RIGHT);
                    }
                    else {
                        badGuess(Direction.DOWN);
                    }
                    break;
            }
        }

        else {
            endKillMode();}
    }

    private void goodGuess(Direction direction1, Direction direction2){
        aimedShot=true;
        directions.remove(direction1);
        directions.remove(direction2);
        possibilitiesLeft -= 2;
    }

    private void badGuess(Direction direction){
        aimedShot=false;
        possibilitiesLeft -=1;
        directions.remove(direction);

    }

    private void endKillMode(){
        killMode=false;
        directions.clear();
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        possibilitiesLeft = directions.size();
        generateShoot();
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
