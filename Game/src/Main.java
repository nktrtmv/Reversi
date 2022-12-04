import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("\n=======================================================================\n");
            Game game = new Game();
            game.play();
            System.out.println("\n=======================================================================\n");
            System.out.println("Если вы желаете сыграть еще одну партию - введите 'continue', иначе игра завершится");
        } while (input.next().equals("continue"));
    }
}