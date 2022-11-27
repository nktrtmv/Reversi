import java.util.ArrayList;
import java.util.Scanner;

public class User implements Player {
    @Override
    public Pair<Integer, Integer> chooseMove(boolean[][] possibleMoves) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possibleMoves[j][i]) {
                    moves.add(new Pair<>(j, i));
                }
            }
        }

        if (moves.isEmpty()) {
            System.out.println("У вас нет ходов. Вы пропускаете ход.");
            return null;
        }

        System.out.print("Выберите следующий ход: ");
        Integer temp;

        do {
            temp = Game.inputInt(1, moves.size());
        } while (temp == null);

        int move = temp;

        return moves.get(move - 1);
    }
}
