package Lesson4_27_09_2015.homework.seaBattle;

public class Ship {

    private Cell[] cells;
    private int unexplodedDecks;

    public Ship(int x, int y, int shipLen, int shipHi) {
        cells = new Cell[Math.max(shipLen, shipHi)];
        fillCells(x, y, shipLen, shipHi);
        unexplodedDecks = cells.length;
    }

    public Cell[] getCells() {
        Cell[] clone = cells.clone();
        for (int i = 0; i < clone.length; i++) {
            clone[i] = clone[i].clone();
        }
        return clone;
    }

    public int getUnexplodedDecks() {
        return unexplodedDecks;
    }

    public void hit() {
        unexplodedDecks--;
    }

    private void fillCells(int x, int y, int shipLen, int shipHi) {
        int count = 0;
        for (int i = y; i < shipHi + y; i++) {
            for (int j = x; j < shipLen + x; j++) {
                cells[count++] = new Cell(j, i);
            }
        }
    }
}