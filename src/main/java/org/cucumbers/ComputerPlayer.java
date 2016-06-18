package org.cucumbers;

/**
 * Created by Mateusz on 2016-06-18.
 */

public interface ComputerPlayer extends Player {
    int[] generateShoot();
    void fillBoard();
}
