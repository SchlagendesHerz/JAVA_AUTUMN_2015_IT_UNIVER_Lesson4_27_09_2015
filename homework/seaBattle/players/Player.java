package Lesson4_27_09_2015.homework.seaBattle.players;

import Lesson4_27_09_2015.homework.seaBattle.Cell;
import Lesson4_27_09_2015.homework.seaBattle.Field;

import java.io.IOException;

public abstract class Player {

    protected Field own;
    protected Field opponent;

    public Player() {
        this.own = new Field();
        this.opponent = new Field();
    }

    public void arrangeShips(){
        own.fillRandom();
    }

    public abstract Cell requestTarget() throws IOException;

    public boolean respondTarget(Cell target) {
        return own.fire(target);
    }

    public void show(int cellWidth, int cellHeight, int opIndent, int ownIndent) {
        String[] op = this.opponent.show(cellWidth, cellHeight, opIndent).split(System.lineSeparator());
        String[] own = this.own.show(cellWidth, cellHeight, ownIndent).split(System.lineSeparator());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < op.length; i++) {
            sb.append(op[i]).append(own[i]);
            if (i < op.length - 1){
                sb.append(System.lineSeparator());
            }
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws IOException {
        new Player() {

            @Override
            public void arrangeShips() {

            }

            @Override
            public Cell requestTarget() {
                return null;
            }
        }.show(3, 1, 10, 10);
    }
}
