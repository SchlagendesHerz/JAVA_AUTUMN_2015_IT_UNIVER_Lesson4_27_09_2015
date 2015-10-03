package Lesson4_27_09_2015.homework.seaBattle.players;

import Lesson4_27_09_2015.homework.seaBattle.Cell;

public class Computer extends Player {

    @Override
    public Cell requestTarget() {
        int x, y;
        Cell toReturn;
        x = (int) (Math.random() * 10);
        y = (int) (Math.random() * 10);
        for (int i = y; i < 10; i++) {
            for (int j = x; j < 10; j++) {
                if (!opponent.isMarked(toReturn = new Cell(i, j))) {
                    return toReturn;
                }
            }
            x = 0;
            if (y == 9) {
                y = 0;
            }
        }
        return null;
    }
}