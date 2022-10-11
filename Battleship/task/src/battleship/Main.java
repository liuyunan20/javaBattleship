package battleship;

import java.util.*;

public class Main {



    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1, place your ships on the game field");
        Player player1 = new Player(1);

        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();

        System.out.println("Player 2, place your ships to the game field\n");
        Player player2 = new Player(2);

        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        String winner, loser;
        while (true) {
            player1.play(player2);
            if (player2.field.checkFail()) {
                winner = "Player 1";
                loser = "Player 2";
                break;
            }
            System.out.println("Press Enter and pass the move to another player\n");
            scanner.nextLine();
            player2.play(player1);
            if (player1.field.checkFail()) {
                winner = "Player 2";
                loser = "Player 1";
                break;
            }
            System.out.println("Press Enter and pass the move to another player\n");
            scanner.nextLine();
        }

        System.out.printf("%s sank the last ship. %s won. Congratulations!", loser, winner);
    }
}
