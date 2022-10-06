package battleship;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    static final Map<String, Integer> ships =
            new TreeMap<>(Map.of("Aircraft Carrier", 5,
                    "Battleship", 4,
                    "Submarine", 3,
                    "Cruiser", 3,
                    "Destroyer", 2));
    static int[][] parseInput(String input) {
        String start = input.split("\\s+")[0];
        String end = input.split("\\s+")[1];
        int startR, startC, endR, endC;

        int[][] coo = new int[2][2];
        if (start.charAt(0) == 'A') {
            startR = 0;
        } else {
            startR = start.charAt(0) - 'A';
        }
        if (end.charAt(0) == 'A') {
            endR = 0;
        } else {
            endR = start.charAt(0) - 'A';
        }
        startC = Integer.parseInt(start.substring(1)) - 1;
        endC = Integer.parseInt(end.substring(1)) - 1;
        coo[0][0] = Math.min(startR, endR);
        coo[0][1] = Math.min(startC,endC);
        coo[1][0] = Math.max(startR, endR);
        coo[1][1] = Math.max(startC, endC);
        return coo;
    }

    static boolean checkInputValid(int[][] coo, Field field, String name, int length) {
        if (coo[0][0] != coo[1][0] &&
                coo[0][1] != coo[1][1]) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }
        if (Math.abs(coo[0][0] - coo[1][0]) != length - 1 && Math.abs(coo[0][1] - coo[1][1]) != length - 1) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
            return false;
        }

        int rowStart = Math.max(coo[0][0] - 1, 0);
        int rowEnd = Math.min(coo[1][0] + 1, 10);
        int colStart = Math.max(coo[0][1] - 1, 0);
        int colEnd = Math.min(coo[1][1] + 1, 10);
        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = colStart; j < colEnd; j++) {
                if (field.getSymbol(i, j) == '0') {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Field field = new Field();
        field.printField();

        for (Map.Entry<String, Integer> set: ships.entrySet()) {
            String shipName = set.getKey();
            Integer shipLength = set.getValue();
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", shipName, shipLength);
            String input = scanner.nextLine();
            int[][] coo = parseInput(input);
            while (!checkInputValid(coo, field, shipName, shipLength)) {
                coo = parseInput(scanner.nextLine());
            }
            field.setField(coo);
            field.printField();
        }
    }
}
