package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Player {
    // define ships and length of ships
    final List<Ship> ships = new ArrayList<>();
    Field field;
    int turn;

    public Player(int turn) {
        this.turn = turn;
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));
        this.field = placeShips();
    }

    static Integer[][] parseShipInput(String input) {
        String start = input.split("\\s+")[0];
        String end = input.split("\\s+")[1];
        int startR, startC, endR, endC;

        Integer[][] coo = new Integer[2][2];
        if (start.charAt(0) == 'A') {
            startR = 0;
        } else {
            startR = start.charAt(0) - 'A';
        }
        if (end.charAt(0) == 'A') {
            endR = 0;
        } else {
            endR = end.charAt(0) - 'A';
        }
        startC = Integer.parseInt(start.substring(1)) - 1;
        endC = Integer.parseInt(end.substring(1)) - 1;
        coo[0][0] = Math.min(startR, endR);
        coo[0][1] = Math.min(startC,endC);
        coo[1][0] = Math.max(startR, endR);
        coo[1][1] = Math.max(startC, endC);
        return coo;
    }

    boolean checkShipInputValid(Integer[][] coo, Field field, String name, int length) {
        if (!coo[0][0].equals(coo[1][0]) &&
                !coo[0][1].equals(coo[1][1])) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        if (Math.abs(coo[0][0] - coo[1][0]) != length - 1 && Math.abs(coo[0][1] - coo[1][1]) != length - 1) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
            return false;
        }

        int rowStart = Math.max(coo[0][0] - 1, 0);
        int rowEnd = Math.min(coo[1][0] + 2, 10);
        int colStart = Math.max(coo[0][1] - 1, 0);
        int colEnd = Math.min(coo[1][1] + 2, 10);
        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = colStart; j < colEnd; j++) {
                if (field.getSymbol(new Integer[] {i, j}) == 'O') {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }
        return true;
    }

    Field placeShips() {

        Scanner scanner = new Scanner(System.in);

        Field field = new Field();
        field.printField(); // print original grids

        for (Ship ship: this.ships) {
            String shipName = ship.name;
            int shipLength = ship.length;
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", shipName, shipLength);
            // input coordinates of every ship
            Integer[][] coo = parseShipInput(scanner.nextLine());
            while (!checkShipInputValid(coo, field, shipName, shipLength)) {
                coo = parseShipInput(scanner.nextLine());
            }
            // set the coordinates for ship
            ship.setCoordinates(coo);
            // place ship in specific position and print new grids
            field.setField(coo);
            field.printField();
        }
        return field;
    }

    static Integer[] parseShotInput(String position) {
        try {
            int row, col;
            if (position.charAt(0) == 'A') {
                row = 0;
            } else {
                row = position.charAt(0) - 'A';
            }
            col = Integer.parseInt(position.substring(1)) - 1;
            return new Integer[] {row, col};
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }


    }

    static boolean checkShotInputValid(Integer[] position) {
        int row = position[0];
        int col = position[1];
        if (row < 0 || row > 9 || col < 0 || col > 9) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        return true;
    }

    void shot(Player opponent) {
        Scanner scanner = new Scanner(System.in);
        Integer[] shotPosition = parseShotInput(scanner.nextLine());
        while (shotPosition == null || !checkShotInputValid(shotPosition)) {
            shotPosition = parseShotInput(scanner.nextLine());
        }
        // get a hit
        if (opponent.field.getSymbol(shotPosition) == 'O' || opponent.field.getSymbol(shotPosition) == 'X') {
            // change the field status
            opponent.field.setSymbol(shotPosition, 'X');
//            System.out.println("You hit a ship!");
            // check which ship the hit position belongs to, and check if it's sank
            for (Ship ship: opponent.ships) {
//                System.out.println("check ship");
//                System.out.println(Arrays.toString(shotPosition));
//                for (Integer[] coo : ship.getCoordinates()) {
//                    System.out.println(Arrays.toString(coo));
//                }
//                System.out.println(ship.getCoordinates().contains(shotPosition));
                Integer[] finalShotPosition = shotPosition;
                if (ship.getCoordinates().stream().anyMatch(a -> Arrays.equals(a, finalShotPosition))) {
//                    System.out.println("check contain");
                    if (ship.checkSank(opponent.field)) {
                        System.out.println("You sank a ship!");
                    } else {
                        System.out.println("You hit a ship!");
                    }
                }
            }
        } else { // get missing
            opponent.field.setSymbol(shotPosition, 'M');
            System.out.println("You missed!");
        }
    }

    public void play(Player opponent) {
        opponent.field.printFogField();
        System.out.println("---------------------");
        this.field.printField();
        System.out.printf("Player %d, it's your turn:\n", turn);
        shot(opponent);
    }
}
