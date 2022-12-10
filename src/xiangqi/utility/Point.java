package xiangqi.utility;

/**
 * Represents a coordinate pair of integers.
 */
public class Point {
    public int x;
    public int y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point p) {
        return new Point(this.x + p.x, this.y + p.y);
    }

    Point scale(int i) {
        return new Point(i * this.x, i * this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        return p.x == this.x && p.y == this.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
