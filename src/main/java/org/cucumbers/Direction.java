package org.cucumbers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Created by Mateusz on 2016-06-18. */

enum Direction {
    LEFT(-1, 0, 0),RIGHT(1, 0, 1),UP(0, -1, 0),DOWN(0, 1, 1);

    private final int xDiff;
    private final int yDiff;
    private final int index;

    Direction(int x, int y, int index){
        xDiff = x;
        yDiff = y;
        this.index = index;
    }

    public int[] getDifference(){
        return new int[]{xDiff, yDiff};
    }

    public int index() {return index;}

    private static final List<Direction> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));

    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    public static Direction random()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Direction randomVertically(){
        return VALUES.get(RANDOM.nextInt(2));
    }

    public static Direction randomHorizontally(){
        return VALUES.get(RANDOM.nextInt(2) + 2);
    }
}
