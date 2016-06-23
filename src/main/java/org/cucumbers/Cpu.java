package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.Random;

/** Created by Mateusz on 2016-06-22. */

public class Cpu extends User implements ComputerPlayer {

    private Random random;
    private int boardSize;

    private boolean finishShip;
    private int[] targetCoordinates;
    private Orientation orientation;
    private int[][] edgeCoordinates;
    private int lastIndex;



    @Autowired
    public Cpu(Board board, ShipsFactory shipsFactory) {
        super(board, shipsFactory);

        random = new Random();
        boardSize = board.getSize();

        finishShip = false;
        targetCoordinates = new int[2];
        orientation = Orientation.UNDEFINED;
        edgeCoordinates = new int[2][2];
    }

    @Override
    @Autowired
    @Qualifier("player")
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    @Override
    public int[] generateShoot() {

        int[] nextShoot;
        if (this.finishShip){

            int x = targetCoordinates[0];
            int y = targetCoordinates[1];

            if (opponent.targetIsAlive(x, y)){
                System.out.println("finish");
                nextShoot = destroyShip();
            } else {
                finishShip = false;
                orientation = Orientation.UNDEFINED;
                //todo: reset edge

                this.targetCoordinates = new int[2];
                System.out.println("random1");
                nextShoot = randomShoot();
            }

        } else {
            System.out.println("random2");
            nextShoot = randomShoot();
        }

        return nextShoot;
    }

    private int[] randomShoot() {
        int[] nextShoot = new int[]{random.nextInt(boardSize), random.nextInt(boardSize)};

        if (!opponent.canShootHere(nextShoot[0], nextShoot[1])){
            return randomShoot();
        }

        return nextShoot;
    }

    private int[] destroyShip(){
        try {
            switch (orientation) {

                case UNDEFINED: {
                    return typeNextShoot(Direction.random());
                }

                case VERTICALLY: {
                    return typeNextShoot(Direction.randomVertically());
                }

                case HORIZONTALLY: {
                    return typeNextShoot(Direction.randomHorizontally());
                }
            }
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return destroyShip();
        }
        return null;
    }

    private int[] typeNextShoot(Direction direction){

        int[] nextShoot = new int[2];

        lastIndex = direction.index();
        int[] difference = direction.getDifference();
        int xDiff = difference[0];
        int yDiff = difference[1];

            nextShoot[0] = edgeCoordinates[lastIndex][0] + xDiff;
            nextShoot[1] = edgeCoordinates[lastIndex][1] + yDiff;


        if (!opponent.canShootHere(nextShoot[0], nextShoot[1])){
            System.out.println("error: " + nextShoot[0] + ", " + nextShoot[1]);
            throw new RuntimeException("Field have already been hit!");
        }

        return nextShoot;
    }

    @Override
    public boolean shootTo(int x, int y){

        boolean hit = super.shootTo(x, y);

        if (hit){

            this.edgeCoordinates[lastIndex][0] = x;
            this.edgeCoordinates[lastIndex][1] = y;


            if(!this.finishShip){

                this.finishShip = true;

                this.targetCoordinates[0] = x;
                this.targetCoordinates[1] = y;

                this.edgeCoordinates[0][0] = targetCoordinates[0];
                this.edgeCoordinates[0][1] = targetCoordinates[1];
                this.edgeCoordinates[1][0] = targetCoordinates[0];
                this.edgeCoordinates[1][1] = targetCoordinates[1];

            }

            if (!Arrays.equals(edgeCoordinates[0], edgeCoordinates[1])){
                if (edgeCoordinates[0][0] == edgeCoordinates[1][0]){
                    orientation = Orientation.HORIZONTALLY;
                } else {
                    orientation = Orientation.VERTICALLY;
                }
            }
        }


        return hit;
    }

    @Override
    public boolean isHuman(){
        return false;
    }
}
