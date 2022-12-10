package xiangqi.model;

import xiangqi.utility.Point;

public class Move {
    public Piece piece;
    public Piece target;

    public Point start;
    public Point end;

    public Move(Piece p, Piece t, Point s, Point e) {
        this.piece = p;
        this.target = t;
        this.start = s;
        this.end = e;
    }

    public String toString() {
        if (piece != null && piece.code != 0) {
            String pieceSymbol = "";
            switch (Math.abs(piece.code)) {
                case 1 -> {
                    pieceSymbol = "G";
                }
                case 2 -> {
                    pieceSymbol = "A";
                }
                case 3 -> {
                    pieceSymbol = "E";
                }
                case 4 -> {
                    pieceSymbol = "H";
                }
                case 5 -> {
                    pieceSymbol = "R";
                }
                case 6 -> {
                    pieceSymbol = "C";
                }
                case 7 -> {
                    pieceSymbol = "S";
                }
            }
            if (piece.code > 0) {
                return pieceSymbol + " (" + (start.x + 1) + (start.y + 1) + ")" + "-" + (end.x + 1)
                        + (end.y + 1);
            } else {
                return pieceSymbol + " (" + (10 - start.x) + (9 - start.y) + ")" + "-"
                        + (10 - end.x) + (9 - end.y);
            }
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Move m))
            return false;

        boolean eq = true;
        if (this.piece == null) {
            if (m.piece != null) {
                eq = false;
            }
        } else {
            if (m.piece == null) {
                eq = false;
            } else if (this.piece.code != m.piece.code) {
                eq = false;
            }
        }
        if (!this.start.equals(m.start)) {
            eq = false;
        }
        if (!this.end.equals(m.end)) {
            eq = false;
        }
        return eq;
    }
}
