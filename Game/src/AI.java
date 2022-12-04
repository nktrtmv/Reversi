import java.util.ArrayList;

/**
 * Искуственный интелект - игрок компьютер
 */
public class AI implements User {
    /**
     * Уровень сложности
     */
    private final boolean isHard;

    /**
     * Цвет фишек игрока
     */
    private final int color;

    public AI(boolean isHard, int color) {
        this.isHard = isHard;
        this.color = color;
    }

    @Override
    public Point makeMove(boolean[][] possibleMoves, Board board) {
        ArrayList<Point> moves = User.getListOfPossibleMoves(possibleMoves);

        if (moves.isEmpty()) {
            System.out.println("У компьютера нет ходов. Компьютер пропускает ход.");
            return null;
        }

        int boardValue = board.calculateData(color);
        Point total = null;

        double maxTemp = 0;
        int count = 0;
        int totalCount = 0;
        for (Point move : moves) {
            count++;
            int y = move.y(), x = move.x();
            Board temp = new Board(board.data);
            temp.data[y][x] = color;
            temp.update(color, move);
            int tempValue = temp.calculateData(color);
            double rValue = tempValue - boardValue;
            if (y == 0 && x == 0 || y == 7 && x == 7 || y == 7 && x == 0 || y == 0 && x == 7) {
                rValue += 0.8;
            } else if (y == 0 || x == 0 || y == 7 || x == 7) {
                rValue += 0.4;
            }

            if (isHard) {
                rValue -= calculateMaxNextMove(temp);
            }

            if (rValue > maxTemp) {
                total = move;
                totalCount = count;
                maxTemp = rValue;
            }
        }
        System.out.printf("Сделан ход: '" + totalCount + "' -- x = " + total.x() + "; y = " + total.y() + "\n");
        return total;
    }

    /**
     * Подсчитать количество очков по формуле для следующего хода (хода противника). Используется для реализации высокой сложности игры
     */
    private double calculateMaxNextMove(Board board) {
        ArrayList<Point> moves = User.getListOfPossibleMoves(Board.getPossibleMoves(-color, board.data));

        if (moves.isEmpty()) {
            return 0;
        }

        int boardValue = board.calculateData(color);
        double maxTemp = 0;

        for (Point move : moves) {
            int y = move.y(), x = move.x();
            Board temp = new Board(board.data);
            temp.data[y][x] = color;
            temp.update(color, move);
            int tempValue = temp.calculateData(color);
            double rValue = tempValue - boardValue;
            if (y == 0 && x == 0 || y == 7 && x == 7 || y == 7 && x == 0 || y == 0 && x == 7) {
                rValue += 0.8;
            } else if (y == 0 || x == 0 || y == 7 || x == 7) {
                rValue += 0.4;
            }
            if (rValue > maxTemp) {
                maxTemp = rValue;
            }
        }

        return maxTemp;
    }
}
