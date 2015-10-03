package Lesson4_27_09_2015.homework.seaBattle;

public final class Cell implements Cloneable {

    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public Cell clone() {
        Cell clone;
        try {
            clone = (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return clone;
    }

    @Override
    public boolean equals(Object other) {
        return (this.getClass() == other.getClass() &&
                this.x == ((Cell) other).x &&
                this.y == ((Cell) other).y);
    }

    @Override
    public int hashCode() {
        return x + 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + "." + y + ")";
    }
}