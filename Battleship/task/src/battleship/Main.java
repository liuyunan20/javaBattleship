package battleship;

import java.util.*;

public class Main {

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

    static boolean checkShipInputValid(Integer[][] coo, Field field, String name, int length) {
        if (!coo[0][0].equals(coo[1][0]) &&
                !coo[0][1].equals(coo[1][1])) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        if (Math.abs(coo[0][0] - coo[1][0]) != length - 1 && Math.abs(coo[0][1] - coo[1][1]) != length - 1) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
            return false;
        }

        int rowStart = Math.max(coo[0][0] - 2, 0);
        int rowEnd = Math.min(coo[1][0] + 2, 10);
        int colStart = Math.max(coo[0][1] - 2, 0);
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

    static Integer[] parseShotInput(String position) {
        int row, col;
        if (position.charAt(0) == 'A') {
            row = 0;
        } else {
            row = position.charAt(0) - 'A';
        }
        col = Integer.parseInt(position.substring(1)) - 1;
        return new Integer[] {row, col};

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

    static Field placeShips(List<Ship> ships) {
        Scanner scanner = new Scanner(System.in);

        Field field = new Field();
        field.printField(); // print original grids

        for (Ship ship: ships) {
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

    public static void main(String[] args) {
        // define ships and length of ships
        final List<Ship> ships = new ArrayList<>();
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));

        System.out.println("Player 1, place your ships on the game field");
        Field field1 = placeShips(ships);
        System.out.println("Press Enter and pass the move to another player\n" +
                "...\n" +
                "Player 2, place your ships to the game field\n");
        Field field2 = placeShips(ships);

        System.out.println("The game starts!");
        field.printFogField();
        System.out.println("Take a shot!");
        // start to check if all ship sank
        while (!field.checkWin()) {
            Integer[] shotPosition = parseShotInput(scanner.nextLine());
            while (!checkShotInputValid(shotPosition)) {
                shotPosition = parseShotInput(scanner.nextLine());
            }
            if (field.getSymbol(shotPosition) == 'O' || field.getSymbol(shotPosition) == 'X') { // get a hit
                // change the field status
                field.setSymbol(shotPosition, 'X');
                field.printFogField();
                if (field.checkWin()) {
                    break;
                }
                // check which ship the hit position belongs to, and check if it's sank
                for (Ship ship: ships) {
                    if (ship.getCoordinates().contains(shotPosition)) {
                        if (ship.checkSank(field)) {
                            System.out.println("You sank a ship! Specify a new target:");
                        } else {
                            System.out.println("You hit a ship! Try again:");
                        }
                    }
                }
            } else { // get missing
                field.setSymbol(shotPosition, 'M');
                field.printFogField();
                System.out.println("You missed! Try again:");
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}
