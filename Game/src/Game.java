import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс отвечающий за процесс игры и все что с этим связано
 */
public class Game {
    /**
     * Константа - режим игры против игрока
     */
    private static final int PVP = 1;

    /**
     * Константа - режим игры против компьютера
     */
    private static final int PVE = 2;

    /**
     * Константа - режим игры против компьютера (сложно)
     */
    private static final int HARD_PVE = 3;

    /**
     * Константа - черные
     */
    private static final int BLACK = -1;

    /**
     * Константа - белые
     */
    private static final int WHITE = 1;

    /**
     * Игроки за белые и черные фишки -
     */
    private User black, white;

    /**
     * Текущий игрок (определение чей ход)
     */
    private int currentPLayer;

    /**
     * Игровая доска
     */
    private Board board;

    /**
     * Список игровых досок (ходов)
     */
    private final ArrayList<Board> allMoves;

    static int maxRes = 0, maxResPlayer = BLACK;

    /**
     * Вводит числа в диапазоне
     */
    public static Integer inputInt(int left, int right) {
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
        allMoves = new ArrayList<>();
        allMoves.add(new Board(board.data));
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

        int mode = temp;

        if (mode == PVP) {
            black = new Player(BLACK);
            white = new Player(WHITE);
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
                black = new Player(BLACK);
                switch (mode) {
                    case PVE -> white = new AI(false, WHITE);
                    case HARD_PVE -> white = new AI(true, WHITE);
                }
            }
            case 2 -> {
                white = new Player(WHITE);
                switch (mode) {
                    case PVE -> black = new AI(false, BLACK);
                    case HARD_PVE -> black = new AI(true, BLACK);
                }
            }
        }
    }

    /**
     * Процесс игры
     */
    public void play() {
        boolean flag = false;
        while (!isOver(flag)) {
            boolean[][] possibleMoves = Board.getPossibleMoves(currentPLayer, board.data);
            Point score = board.countPoints();
            Scanner input = new Scanner(System.in);
            System.out.println("\n========================================================================");
            System.out.println("\nЧерные - " + score.x() + ", Белые - " + score.y());
            System.out.println("\nХод " + (currentPLayer == BLACK ? "черных:" : "белых:"));
            if (allMoves.size() > 2) {
                board.print();
                System.out.println("\n========================================================================");
                System.out.println("""

                        Вы можете отменить последние два хода (ваш и вашего противника)
                        Для отмены хода введите 'yes', для продолжения игры введите любую другую строку""");
                if (input.next().equals("yes")) {
                    board = new Board(allMoves.get(allMoves.size() - 3).data);
                    possibleMoves = Board.getPossibleMoves(currentPLayer, board.data);
                    allMoves.remove(allMoves.size() - 1);
                    allMoves.remove(allMoves.size() - 1);
                }
            }
            flag = User.getListOfPossibleMoves(possibleMoves).isEmpty();
            board.print(possibleMoves);
            makeMove(possibleMoves);
            score = board.countPoints();
            if (score.x() > maxRes) {
                maxRes = score.x();
                maxResPlayer = BLACK;
            }
            if (score.y() > maxRes) {
                maxRes = score.y();
                maxResPlayer = WHITE;
            }
            allMoves.add(new Board(board.data));
            currentPLayer *= -1;
            System.out.println("""

                    ==============================================================
                    Если вы желаете завершить партию введите "exit", если вы желаете продолжить игру - введите любую другую строку
                    ==============================================================
                    """);
            if (input.next().equals("exit")) {
                break;
            }
        }
        board.print();
        Point score = board.countPoints();
        System.out.println("\nЧерные - " + score.x() + ", Белые - " + score.y());
        System.out.println("Максимальный результат за сессию: " + maxRes + " - " + (maxResPlayer == BLACK ? "черные" : "белые"));
        System.out.println("Game Over");
    }

    /**
     * Сделать ход
     */
    private void makeMove(boolean[][] possibleMoves) {
        Point move = null;
        switch (currentPLayer) {
            case BLACK -> move = black.makeMove(possibleMoves, board);
            case WHITE -> move = white.makeMove(possibleMoves, board);
        }

        if (move != null) {
            board.data[move.y()][move.x()] = currentPLayer;
            board.update(currentPLayer, move);
        }
    }

    /**
     * Закончена ли игра - есть ли победитель
     */
    private boolean isOver(boolean flag) {
        return board.isFull() || User.getListOfPossibleMoves(Board.getPossibleMoves(currentPLayer, board.data)).isEmpty() && flag;
    }
}
