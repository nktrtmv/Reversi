import java.util.ArrayList;

/**
 * Игровое поле - доска
 */
public class Board {

    /**
     * Константа - черные
     */
    private static final int BLACK = -1;

    /**
     * Константа - пустая клетка
     */
    private static final int EMPTY = 0;

    /**
     * Константа - белые
     */
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

    public Board(int[][] temp) {
        data = new int[8][];
        for (int i = 0; i < 8; i++) {
            data[i] = new int[8];
            System.arraycopy(temp[i], 0, data[i], 0, 8);
        }
    }

    /**
     * Считает количество очков по формуле на всем поле
     */
    public int calculateData(int currentPlayer) {
        int temp = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (data[y][x] == currentPlayer) {
                    if (y == 0 || x == 0 || y == 7 || x == 7) {
                        temp++;
                    }
                    temp++;
                }
            }
        }
        return temp;
    }

    /**
     * Двумерный массив - игровое поле
     */
    public int[][] data;

    /**
     * Получение возможных ходов игрока
     */
    public static boolean[][] getPossibleMoves(int currentPlayer, int[][] data) {
        boolean[][] possibleMoves = new boolean[8][];
        for (int y = 0; y < 8; y++) {
            possibleMoves[y] = new boolean[8];
            for (int x = 0; x < 8; x++) {
                possibleMoves[y][x] = false;
            }
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (data[y][x] == currentPlayer) {
                    Point temp;
                    int colorCheck = -data[y][x];

                    temp = checkLeftMainDiagonal(x, y, colorCheck, data);
                    if (temp.x() != x && temp.y() != y) {
                        possibleMoves[temp.y()][temp.x()] = true;
                    }

                    temp = checkRightMainDiagonal(x, y, colorCheck, data);
                    if (temp.x() != x && temp.y() != y) {
                        possibleMoves[temp.y()][temp.x()] = true;
                    }

                    temp = checkLeftSideDiagonal(x, y, colorCheck, data);
                    if (temp.x() != x && temp.y() != y) {
                        possibleMoves[temp.y()][temp.x()] = true;
                    }

                    temp = checkRightSideDiagonal(x, y, colorCheck, data);
                    if (temp.x() != x && temp.y() != y) {
                        possibleMoves[temp.y()][temp.x()] = true;
                    }

                    temp = checkDown(x, y, colorCheck, data);
                    if (temp.y() != y) {
                        possibleMoves[temp.y()][x] = true;
                    }

                    temp = checkUp(x, y, colorCheck, data);
                    if (temp.y() != y) {
                        possibleMoves[temp.y()][x] = true;
                    }

                    temp = checkLeft(x, y, colorCheck, data);
                    if (temp.x() != x) {
                        possibleMoves[y][temp.x()] = true;
                    }

                    temp = checkRight(x, y, colorCheck, data);
                    if (temp.x() != x) {
                        possibleMoves[y][temp.x()] = true;
                    }
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Проверка главной диагонали налево наверх
     */
    private static Point checkLeftMainDiagonal(int x, int y, int colorCheck, int[][] data) {
        int i = x, j = y;
        while (x > 0 && y > 0 && data[y - 1][x - 1] == colorCheck) {
            x--;
            y--;
        }

        if (x == i && y == j) {
            return new Point(x, y);
        }

        if (x > 0 && y > 0 && data[y - 1][x - 1] == EMPTY) {
            return new Point(x - 1, y - 1);
        }

        return new Point(i, j);
    }

    /**
     * Проверка главной диагонали направо вниз
     */
    private static Point checkRightMainDiagonal(int x, int y, int colorCheck, int[][] data) {
        int i = x, j = y;
        while (x < 7 && y < 7 && data[y + 1][x + 1] == colorCheck) {
            x++;
            y++;
        }

        if (x == i && y == j) {
            return new Point(x, y);
        }

        if (x < 7 && y < 7 && data[y + 1][x + 1] == EMPTY) {
            return new Point(x + 1, y + 1);
        }

        return new Point(i, j);
    }

    /**
     * Проверка побочной диагонали налево вниз
     */
    private static Point checkLeftSideDiagonal(int x, int y, int colorCheck, int[][] data) {
        int i = x, j = y;
        while (x > 0 && y < 7 && data[y + 1][x - 1] == colorCheck) {
            x--;
            y++;
        }

        if (x == i && y == j) {
            return new Point(x, y);
        }

        if (x > 0 && y < 7 && data[y + 1][x - 1] == EMPTY) {
            return new Point(x - 1, y + 1);
        }

        return new Point(i, j);
    }

    /**
     * Проверка побочной диагонали направо наверх
     */
    private static Point checkRightSideDiagonal(int x, int y, int colorCheck, int[][] data) {
        int i = x, j = y;
        while (x < 7 && y > 0 && data[y - 1][x + 1] == colorCheck) {
            x++;
            y--;
        }

        if (x == i && y == j) {
            return new Point(x, y);
        }

        if (x < 7 && y > 0 && data[y - 1][x + 1] == EMPTY) {
            return new Point(x + 1, y - 1);
        }

        return new Point(i, j);
    }

    /**
     * Проверка вертикали вниз
     */
    private static Point checkDown(int x, int y, int colorCheck, int[][] data) {
        int j = y;
        while (y < 7 && data[y + 1][x] == colorCheck) {
            y++;
        }

        if (y == j) {
            return new Point(x, y);
        }

        if (y < 7 && data[y + 1][x] == EMPTY) {
            return new Point(x, y + 1);
        }

        return new Point(x, j);
    }

    /**
     * Проверка вертикали вверх
     */
    private static Point checkUp(int x, int y, int colorCheck, int[][] data) {
        int j = y;
        while (y > 0 && data[y - 1][x] == colorCheck) {
            y--;
        }

        if (y == j) {
            return new Point(x, y);
        }

        if (y > 0 && data[y - 1][x] == EMPTY) {
            return new Point(x, y - 1);
        }

        return new Point(x, j);
    }

    /**
     * Проверка горизонтали налево
     */
    private static Point checkLeft(int x, int y, int colorCheck, int[][] data) {
        int i = x;
        while (x > 0 && data[y][x - 1] == colorCheck) {
            x--;
        }

        if (x == i) {
            return new Point(x, y);
        }

        if (x > 0 && data[y][x - 1] == EMPTY) {
            return new Point(x - 1, y);
        }

        return new Point(i, y);
    }

    /**
     * Проверка горизонтали направо
     */
    private static Point checkRight(int x, int y, int colorCheck, int[][] data) {
        int i = x;
        while (x < 7 && data[y][x + 1] == colorCheck) {
            x++;
        }

        if (x == i) {
            return new Point(x, y);
        }

        if (x < 7 && data[y][x + 1] == EMPTY) {
            return new Point(x + 1, y);
        }

        return new Point(i, y);
    }

    /**
     * Нарисовать игровое поле в консоли с возможными ходами
     */
    public void print(boolean[][] possibleMoves) {
        int counter = 1;
        for (int y = 0; y < 8; y++) {
            System.out.println("—————————————————————————————————————————————————");
            System.out.print("|");
            for (int x = 0; x < 8; x++) {
                if (possibleMoves[y][x]) {
                    System.out.print("  " + counter++ + "  |");
                } else if (data[y][x] == BLACK) {
                    System.out.print("  ●  |");
                } else if (data[y][x] == WHITE) {
                    System.out.print("  ◯  |");
                } else {
                    System.out.print("     |");
                }
            }
            System.out.println();
        }
        System.out.println("—————————————————————————————————————————————————");
    }

    /**
     * Нарисовать игровое поле в консоли
     */
    public void print() {
        for (int y = 0; y < 8; y++) {
            System.out.println("—————————————————————————————————————————————————");
            System.out.print("|");
            for (int x = 0; x < 8; x++) {
                if (data[y][x] == BLACK) {
                    System.out.print("  ●  |");
                } else if (data[y][x] == WHITE) {
                    System.out.print("  ◯  |");
                } else {
                    System.out.print("     |");
                }
            }
            System.out.println();
        }
        System.out.println("—————————————————————————————————————————————————");
    }

    /**
     * Проверка заполнена ли доска полностью
     */
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

    /**
     * Заполнение линии
     */
    private void fillLine(int currentPlayer, Point start, int yop, int xop) {
        ArrayList<Point> toFill = new ArrayList<>();
        boolean flag = true, ended = false;
        for (int y = start.y() + yop, x = start.x() + xop; y >= 0 && x >= 0 && y < 8 && x < 8; y += yop, x += xop) {
            if (data[y][x] == -currentPlayer) {
                toFill.add(new Point(x, y));
            } else if (data[y][x] == EMPTY) {
                flag = false;
                break;
            } else {
                ended = true;
                break;
            }
        }

        if (flag && ended) {
            for (Point point : toFill) {
                data[point.y()][point.x()] = currentPlayer;
            }
        }
    }

    /**
     * Обновление доски - заполнение нужных ячеек поля после хода
     */
    public void update(int currentPlayer, Point start) {
        fillLine(currentPlayer, start, -1, -1);
        fillLine(currentPlayer, start, 1, 1);
        fillLine(currentPlayer, start, 1, 0);
        fillLine(currentPlayer, start, 1, -1);
        fillLine(currentPlayer, start, 0, 1);
        fillLine(currentPlayer, start, 0, -1);
        fillLine(currentPlayer, start, -1, 1);
        fillLine(currentPlayer, start, -1, 0);
    }

    /**
     * Подсчет количества очков белых и черных
     */
    public Point countPoints() {
        int countBlack = 0, countWhite = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (data[y][x] == BLACK) {
                    countBlack++;
                } else if (data[y][x] == WHITE) {
                    countWhite++;
                }
            }
        }
        return new Point(countBlack, countWhite);
    }
}
