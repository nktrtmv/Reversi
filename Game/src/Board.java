public class Board {

    private static final int BLACK = -1;
    private static final int EMPTY = 0;
    private static final int WHITE = 1;

    public Board() {
        data = new int[8][];
        for (int i = 0; i < 8; i++) {
            data[i] = new int[8];
            for (int j = 0; j < 8; j++) {
                data[i][j] = EMPTY;
            }
        }
        data[3][4] = BLACK;
        data[4][3] = BLACK;
        data[3][3] = WHITE;
        data[4][4] = WHITE;
    }

    public int[][] data;

    public boolean[][] getPossibleMoves(int currentPlayer) {
        boolean[][] possibleMoves = new boolean[8][];
        for (int i = 0; i < 8; i++) {
            possibleMoves[i] = new boolean[8];
            for (int j = 0; j < 8; j++) {
                possibleMoves[i][j] = false;
            }
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (data[y][x] == currentPlayer) {
                    Pair<Integer, Integer> temp;
                    int colorCheck = -data[y][x];

                    temp = checkLeftMainDiagonal(x, y, colorCheck);
                    if (temp.first() != x && temp.second() != y) {
                        possibleMoves[temp.second()][temp.first()] = true;
                    }

                    temp = checkRightMainDiagonal(x, y, colorCheck);
                    if (temp.first() != x && temp.second() != y) {
                        possibleMoves[temp.second()][temp.first()] = true;
                    }

                    temp = checkLeftSideDiagonal(x, y, colorCheck);
                    if (temp.first() != x && temp.second() != y) {
                        possibleMoves[temp.second()][temp.first()] = true;
                    }

                    temp = checkRightSideDiagonal(x, y, colorCheck);
                    if (temp.first() != x && temp.second() != y) {
                        possibleMoves[temp.second()][temp.first()] = true;
                    }

                    temp = checkDown(x, y, colorCheck);
                    if (temp.second() != y) {
                        possibleMoves[temp.second()][x] = true;
                    }

                    temp = checkUp(x, y, colorCheck);
                    if (temp.second() != y) {
                        possibleMoves[temp.second()][x] = true;
                    }

                    temp = checkLeft(x, y, colorCheck);
                    if (temp.first() != x) {
                        possibleMoves[y][temp.first()] = true;
                    }

                    temp = checkRight(x, y, colorCheck);
                    if (temp.first() != x) {
                        possibleMoves[y][temp.first()] = true;
                    }
                }
            }
        }

        return possibleMoves;
    }

    private Pair<Integer, Integer> checkLeftMainDiagonal(int x, int y, int colorCheck) {
        int i = x, j = y;
        while (x > 0 && y > 0 && data[y - 1][x - 1] == colorCheck) {
            x--;
            y--;
        }

        if (x == i && y == j) {
            return new Pair<>(x, y);
        }

        if (x > 0 && y > 0 && data[y - 1][x - 1] == EMPTY) {
            return new Pair<>(x - 1, y - 1);
        }

        return new Pair<>(i, j);
    }

    private Pair<Integer, Integer> checkRightMainDiagonal(int x, int y, int colorCheck) {
        int i = x, j = y;
        while (x < 7 && y < 7 && data[y + 1][x + 1] == colorCheck) {
            x++;
            y++;
        }

        if (x == i && y == j) {
            return new Pair<>(x, y);
        }

        if (x < 7 && y < 7 && data[y + 1][x + 1] == EMPTY) {
            return new Pair<>(x + 1, y + 1);
        }

        return new Pair<>(i, j);
    }

    private Pair<Integer, Integer> checkLeftSideDiagonal(int x, int y, int colorCheck) {
        int i = x, j = y;
        while (x > 0 && y < 7 && data[y + 1][x - 1] == colorCheck) {
            x--;
            y++;
        }

        if (x == i && y == j) {
            return new Pair<>(x, y);
        }

        if (x > 0 && y < 7 && data[y + 1][x - 1] == EMPTY) {
            return new Pair<>(x - 1, y + 1);
        }

        return new Pair<>(i, j);
    }

    private Pair<Integer, Integer> checkRightSideDiagonal(int x, int y, int colorCheck) {
        int i = x, j = y;
        while (x < 7 && y > 0 && data[y - 1][x + 1] == colorCheck) {
            x++;
            y--;
        }

        if (x == i && y == j) {
            return new Pair<>(x, y);
        }

        if (x < 7 && y > 0 && data[y - 1][x + 1] == EMPTY) {
            return new Pair<>(x + 1, y - 1);
        }

        return new Pair<>(i, j);
    }

    private Pair<Integer, Integer> checkDown(int x, int y, int colorCheck) {
        int j = y;
        while (y < 7 && data[y + 1][x] == colorCheck) {
            y++;
        }

        if (y == j) {
            return new Pair<>(x, y);
        }

        if (y < 7 && data[y + 1][x] == EMPTY) {
            return new Pair<>(x, y + 1);
        }

        return new Pair<>(x, j);
    }

    private Pair<Integer, Integer> checkUp(int x, int y, int colorCheck) {
        int j = y;
        while (y > 0 && data[y - 1][x] == colorCheck) {
            y--;
        }

        if (y == j) {
            return new Pair<>(x, y);
        }

        if (y > 0 && data[y - 1][x] == EMPTY) {
            return new Pair<>(x, y - 1);
        }

        return new Pair<>(x, j);
    }

    private Pair<Integer, Integer> checkLeft(int x, int y, int colorCheck) {
        int i = x;
        while (x > 0 && data[y][x - 1] == colorCheck) {
            x--;
        }

        if (x == i) {
            return new Pair<>(x, y);
        }

        if (x > 0 && data[y][x - 1] == EMPTY) {
            return new Pair<>(x - 1, y);
        }

        return new Pair<>(i, y);
    }

    private Pair<Integer, Integer> checkRight(int x, int y, int colorCheck) {
        int i = x;
        while (x < 7 && data[y][x + 1] == colorCheck) {
            x++;
        }

        if (x == i) {
            return new Pair<>(x, y);
        }

        if (x < 7 && data[y][x + 1] == EMPTY) {
            return new Pair<>(x + 1, y);
        }

        return new Pair<>(i, y);
    }

    public void print(boolean[][] possibleMoves) {
        int counter = 1;
        for (int i = 0; i < 8; i++) {
            System.out.println("—————————————————————————————————————————————————");
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                if (possibleMoves[j][i]) {
                    System.out.print("  " + counter++ + "  |");
                } else if (data[j][i] == BLACK) {
                    System.out.print("  ●  |");
                } else if (data[j][i] == WHITE) {
                    System.out.print("  ◯  |");
                } else {
                    System.out.print("     |");
                }
            }
            System.out.println();
        }
        System.out.println("—————————————————————————————————————————————————");
    }

    public boolean isFull() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (data[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fillMainDiagonal(int currentPLayer, Pair<Integer, Integer> move) {
        int x = move.first(), y = move.second();
        if (x > 0 && y > 0) {
            x--;
            y--;
            while (x >= 0 && y >= 0) {
                if (data[y][x] == -currentPLayer) {
                    x--;
                    y--;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int i = move.first() - 1, j = move.second() - 1; i > x && j > y; i--, j--) {
                        data[j][i] = currentPLayer;
                    }
                    break;
                }
            }
        }

        x = move.first();
        y = move.second();
        if (x < 7 && y < 7) {
            x++;
            y++;
            while (x < 8 && y < 8) {
                if (data[y][x] == -currentPLayer) {
                    x++;
                    y++;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int i = move.first() + 1, j = move.second() + 1; i < x && j < y; i++, j++) {
                        data[j][i] = currentPLayer;
                    }
                    break;
                }
            }
        }
    }

    private void fillSideDiagonal(int currentPLayer, Pair<Integer, Integer> move) {
        int x = move.first(), y = move.second();
        if (x > 0 && y < 7) {
            x--;
            y++;
            while (x >= 0 && y < 8) {
                if (data[y][x] == -currentPLayer) {
                    x--;
                    y++;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int i = move.first() - 1, j = move.second() + 1; i > x && j < y; i--, j++) {
                        data[j][i] = currentPLayer;
                    }
                    break;
                }
            }
        }

        x = move.first();
        y = move.second();
        if (x < 7 && y > 0) {
            x++;
            y--;
            while (x < 8 && y >= 0) {
                if (data[y][x] == -currentPLayer) {
                    x++;
                    y--;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int i = move.first() + 1, j = move.second() - 1; i < x && j > y; i++, j--) {
                        data[j][i] = currentPLayer;
                    }
                    break;
                }
            }
        }
    }

    private void fillVertical(int currentPLayer, Pair<Integer, Integer> move) {
        int x = move.first(), y = move.second();
        if (y < 7) {
            y++;
            while (y < 8) {
                if (data[y][x] == -currentPLayer) {
                    y++;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int j = move.second() + 1; j < y; j++) {
                        data[j][x] = currentPLayer;
                    }
                    break;
                }
            }
        }

        y = move.second();
        if (y > 0) {
            y--;
            while (y >= 0) {
                if (data[y][x] == -currentPLayer) {
                    y--;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int j = move.second() - 1; j > y; j--) {
                        data[j][x] = currentPLayer;
                    }
                    break;
                }
            }
        }
    }

    private void fillHorizontal(int currentPLayer, Pair<Integer, Integer> move) {
        int x = move.first(), y = move.second();
        if (x < 7) {
            x++;
            while (x < 8) {
                if (data[y][x] == -currentPLayer) {
                    x++;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int i = move.first() + 1; i < x; i++) {
                        data[y][i] = currentPLayer;
                    }
                    break;
                }
            }
        }

        x = move.first();
        if (x > 0) {
            x--;
            while (x >= 0) {
                if (data[y][x] == -currentPLayer) {
                    x--;
                    continue;
                }
                if (data[y][x] == EMPTY) {
                    break;
                }
                if (data[y][x] == currentPLayer) {
                    for (int i = move.first() - 1; i > x; i--) {
                        data[y][i] = currentPLayer;
                    }
                    break;
                }
            }
        }
    }

    public void update(int currentPlayer, Pair<Integer, Integer> move) {
        print();
        fillMainDiagonal(currentPlayer, move);
        print();
        fillSideDiagonal(currentPlayer, move);
        print();
        fillHorizontal(currentPlayer, move);
        print();
        fillVertical(currentPlayer, move);
        print();
    }

    public Pair<Integer, Integer> countPoints() {
        int countBlack = 0, countWhite = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (data[i][j] == BLACK) {
                    countBlack++;
                } else if (data[i][j] == WHITE) {
                    countWhite++;
                }
            }
        }
        return new Pair<>(countBlack, countWhite);
    }

    public void print() {
        for (int i = 0; i < 8; i++) {
            System.out.println("—————————————————————————————————————————————————");
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                if (data[j][i] == BLACK) {
                    System.out.print("  ●  |");
                } else if (data[j][i] == WHITE) {
                    System.out.print("  ◯  |");
                } else {
                    System.out.print("     |");
                }
            }
            System.out.println();
        }
        System.out.println("—————————————————————————————————————————————————");
    }
}
