import java.util.ArrayList;

/**
 * Класс отвечающий за игрока-человека
 */
public class Player implements User {
    /**
     * Цвет фишек игрока
     */
    private final int color;

    public Player(int color) {
        this.color = color;
    }

    @Override
    public Point makeMove(boolean[][] possibleMoves, Board board) {
        ArrayList<Point> moves = User.getListOfPossibleMoves(possibleMoves);

        if (moves.isEmpty()) {
            System.out.println("У вас нет ходов. Вы пропускаете ход.");
            return null;
        }

        System.out.print("Выберите следующий ход: ");
        Integer temp;

        do {
            temp = Game.inputInt(1, moves.size());
        } while (temp == null);

        return moves.get(temp - 1);
    }
}
