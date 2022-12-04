import java.util.ArrayList;

/**
 * Интерфейс пользователя приложения, пользователем может быть как человек, так и компьютер(AI)
 */
interface User {
    /**
     * Сделать ход в игре
     */
    Point makeMove(boolean[][] possibleMoves, Board board);

    /**
     * Получение списка возможных ходов из двумерного массива
     */
    static ArrayList<Point> getListOfPossibleMoves(boolean[][] possibleMoves) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (possibleMoves[y][x]) {
                    moves.add(new Point(x, y));
                }
            }
        }
        return moves;
    }
}
