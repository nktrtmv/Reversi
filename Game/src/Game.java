import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private static final int PVP = 1;
    private static final int PVE = 2;
    private static final int HARD_PVE = 3;
    private static final int BLACK = -1;
    private static final int WHITE = 1;

    Player black, white;
    int currentPLayer;
    Board board;
    int mode;

    static Integer inputInt(int left, int right) {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Введите число от " + left + " до " + right + ": ");
            int temp = input.nextInt();
            while (temp < left || temp > right) {
                System.out.print("Ошибка! Введите число от " + left + " до " + right + ": ");
                temp = input.nextInt();
            }
            return temp;
        } catch (InputMismatchException ex) {
            return null;
        }
    }


    public Game() {
        board = new Board();
        currentPLayer = BLACK;
        System.out.println("Выберите против кого играть: ");
        System.out.println("""
                1. Против игрока
                2. Против компьютера - низкий уровень сложности
                3. Против компьютера - высокий уровень сложности""");
        Integer temp;

        do {
            temp = inputInt(1, 3);
        } while (temp == null);

        mode = temp;

        if (mode == PVP) {
            black = new User();
            white = new User();
            return;
        }

        System.out.println("Выберите цвет своих фишек:");
        System.out.println("""
                1. Черные
                2. Белые""");

        do {
            temp = inputInt(1, 2);
        } while (temp == null);

        int user = temp;

        switch (user) {
            case 1 -> {
                black = new User();
                switch (mode) {
                    case PVE -> white = new AI(false);
                    case HARD_PVE -> white = new AI(true);
                }
            }
            case 2 -> {
                white = new User();
                switch (mode) {
                    case PVE -> black = new AI(false);
                    case HARD_PVE -> black = new AI(true);
                }
            }
        }
    }

    public void startGame() {
        switch (mode) {
            case PVP -> playPVP();
            case PVE -> playPVE();
            case HARD_PVE -> playHardPVE();
        }
    }

    private void playPVP() {
        while (!isOver()) {
            boolean[][] possibleMoves = board.getPossibleMoves(currentPLayer);
            Pair<Integer, Integer> score = board.countPoints();
            System.out.println("\nЧерные - " + score.first() + ", Белые - " + score.second());
            System.out.println("Ход " + (currentPLayer == BLACK ? "черных:" : "белых:"));
            board.print(possibleMoves);
            makeMove(possibleMoves);
            currentPLayer *= -1;
        }
        System.out.println("Game Over");
    }

    public void playPVE() {

    }

    public void playHardPVE() {

    }

    private void makeMove(boolean[][] possibleMoves) {

        Pair<Integer, Integer> move = null;
        switch (currentPLayer) {
            case BLACK -> move = black.chooseMove(possibleMoves);
            case WHITE -> move = white.chooseMove(possibleMoves);
        }

        if (move != null) {
            board.data[move.first()][move.second()] = currentPLayer;
            board.update(currentPLayer, move);
        }
    }

    private boolean isOver() {
        return board.isFull();
    }
}
