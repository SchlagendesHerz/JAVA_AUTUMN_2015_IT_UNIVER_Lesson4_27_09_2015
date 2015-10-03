package Lesson4_27_09_2015.homework.seaBattle;

import java.io.IOException;
import java.util.*;

public class Field {

    private char[][] field;
    private Map<Cell, Ship> ships;
    private int shipsQuant;

    public Field() {
        this.field = new char[10][10];
        this.ships = new HashMap<>();
    }

    public boolean addShip(int x, int y, int shipLen, boolean hor) {
        if (hor) {
            if (inspectArea(x, y, shipLen, true)) {
                addShip(x, y, shipLen, 1);
                return true;
            }
            if (inspectArea(x, y, shipLen, false)) {
                addShip(x, y, 1, shipLen);
                return true;
            }
        } else {
            if (inspectArea(x, y, shipLen, false)) {
                addShip(x, y, 1, shipLen);
                return true;
            }
            if (inspectArea(x, y, shipLen, true)) {
                addShip(x, y, shipLen, 1);
                return true;
            }
        }
        return false;
    }

    public void fillRandom() {
        List<Integer> shipLen = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 4));
        for (int i = 0; i < 10; i++) {
            int randomIdx, randomX, randomY;
            Random orient = new Random();
            int count = 0;
            do {
                count++;
                randomIdx = (int) (Math.random() * shipLen.size());
                randomX = (int) (Math.random() * 10);
                randomY = (int) (Math.random() * 10);
            } while (!addShip(randomX, randomY, shipLen.get(randomIdx), orient.nextBoolean()));
            System.out.println(shipLen.get(randomIdx) + "-deck ship added, iter-quant = " + count);
            shipsQuant++;
            shipLen.remove(randomIdx);
        }
    }

    public boolean fire(Cell target) {
        if (!isTargetCorrect(target)) {
            return false;
        }

        if (field[target.getY()][target.getX()] == '*' || field[target.getY()][target.getX()] == 'x') {
            return false;
        }
        if (field[target.getY()][target.getX()] == '*' || field[target.getY()][target.getX()] == 'o') {
            hit(target);
            return true;
        }
        markAsMiss(target);
        return false;
    }

    public boolean isEmpty() {
        return shipsQuant == 0;
    }

    public boolean isMarked(Cell cell) {
        if (cell == null) {
            return false;
        }
        int x = cell.getX();
        int y = cell.getY();
        return !(x < 0 || x >= field.length || y < 0 || y >= field.length)
                && (field[y][x] == 'x' || field[y][x] == '*');
    }

    public void markAsMiss(Cell target) {
        if (!isTargetCorrect(target)) {
            return;
        }
        field[target.getY()][target.getX()] = '*';
    }

    public void markAsHit(Cell target) {
        if (!isTargetCorrect(target)) {
            return;
        }
        field[target.getY()][target.getX()] = 'x';
    }

    public String show(int cellWidth, int cellHeight, int indent) {
        if (cellHeight <= 0 || cellHeight <= 0 || indent < 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(makeHeader(cellWidth, indent));
        sb.append(System.lineSeparator());
        sb.append(makeBorderLine(cellWidth, indent));
        sb.append(System.lineSeparator());
        sb.append(makeBody(cellWidth, cellHeight, indent));
        sb.append(System.lineSeparator());
        sb.append(makeHeader(cellWidth, indent));
        return sb.toString();
    }

    private void hit(Cell target) {
        Ship hitted = ships.get(target);
        hitted.hit();
        if (hitted.getUnexplodedDecks() == 0) {
            Cell[] cells = hitted.getCells();
            for (Cell c : cells) {
                ships.remove(c);
            }
            markAsHit(cells);
            shipsQuant--;
        } else
            markAsHit(target);
    }

    private boolean isTargetCorrect(Cell target) {
        if (target == null) {
            return false;
        }
        int x = target.getX();
        int y = target.getY();
        return !(x < 0 || x >= field.length || y < 0 || y >= field.length);
    }

    private boolean inspectArea(int x, int y, int shipLen, boolean horOrient) {
        if (horOrient && x + shipLen - 1 >= field.length) {
            return false;
        }
        if (!horOrient && y + shipLen - 1 >= field.length) {
            return false;
        }
        int areaLen = (horOrient) ? shipLen + 2 : 3;
        int areaHi = (horOrient) ? 3 : shipLen + 2;
        for (int i = 0; i < areaHi; i++) {
            for (int j = 0; j < areaLen; j++) {
                if (!(y - 1 + i >= field.length || y - 1 + i < 0 || x - 1 + j >= field.length || x - 1 + j < 0)) {
                    if ((int) field[y - 1 + i][x - 1 + j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private String makeBody(int cellWidth, int cellHeight, int indent) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int ch = 0; ch < cellHeight; ch++) {
                buf.append(makeIndent(indent));
                if (ch == cellHeight / 2) {
                    buf.append(String.format("%2d", i + 1));
                } else {
                    buf.append("  ");
                }
                buf.append('|');
                for (int j = 0; j < 10; j++) {
                    for (int cw = 0; cw < cellWidth; cw++) {
                        if (ch == cellHeight / 2 && cw == cellWidth / 2) {
                            buf.append((int) field[i][j] == 0 ? ' ' : field[i][j]);
                        } else {
                            buf.append(' ');
                        }
                    }
                    buf.append('|');
                }
                if (ch == cellHeight / 2) {
                    buf.append(String.format("%-2d", i + 1));
                } else {
                    buf.append("  ");
                }
                buf.append(System.lineSeparator());
            }
            buf.append(makeBorderLine(cellWidth, indent));
            if (i != 9) {
                buf.append(System.lineSeparator());
            }
        }
        return buf.toString();
    }

    private String makeBorderLine(int cellWidth, int indent) {
        StringBuilder buf = new StringBuilder(makeIndent(indent));
        buf.append("  +");
        for (int i = 1; i <= 10 * cellWidth; i++) {
            buf.append('-');
            if (i % cellWidth == 0) {
                buf.append('+');
            }
        }
        buf.append("  ");
        return buf.toString();
    }

    private String makeHeader(int cellWidth, int indent) {
        StringBuilder buf = new StringBuilder(makeIndent(indent));
        buf.append("   ");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < cellWidth; j++) {
                if (j == cellWidth / 2) {
                    buf.append((char) (97 + i));
                } else {
                    buf.append(' ');
                }
            }
            buf.append(' ');
        }
        buf.append("  ");
        return buf.toString();
    }

    private String makeIndent(int indent) {
        char[] ar = new char[indent];
        Arrays.fill(ar, ' ');
        return new String(ar);
    }

    private void markAsHit(Cell[] cells) {
        int xBegin = cells[0].getX();
        int yBegin = cells[0].getY();
        int shipLen = (cells[0].getX() == cells[cells.length - 1].getX()) ? 1 : cells.length;
        int shipHi = (shipLen == 1) ? cells.length : 1;
        for (int i = yBegin - 1; i < yBegin + shipHi + 1; i++) {
            for (int j = xBegin - 1; j < xBegin + shipLen + 1; j++) {
                if (!(i < 0 || i >= field.length || j < 0 || j >= field.length)) {
                    field[i][j] = '*';
                }
            }
        }
        for (Cell c : cells) {
            field[c.getY()][c.getX()] = 'x';
        }
    }

    private void addShip(int x, int y, int shipLen, int shipHi) {
        Ship ship = new Ship(x, y, shipLen, shipHi);
        for (int i = y; i < y + shipHi; i++) {
            for (int j = x; j < x + shipLen; j++) {
                field[i][j] = 'o';
                ships.put(new Cell(j, i), ship);
            }
        }
        shipsQuant++;
    }

    public static void main(String[] args) throws IOException {
        Field f = new Field();
        f.fillRandom();
//        f.addShip(4, 9, 2, true);
//        f.addShip(0, 9, 2, false);
        System.out.println(f.show(3, 1, 10));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        while (!f.isEmpty()) {
//            int x = Integer.parseInt(reader.readLine());
//            int y = Integer.parseInt(reader.readLine());
//            f.fire(new Cell(x, y));
//            f.show(3, 1, 0);
//        }
    }
}