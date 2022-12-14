package battleship;

import java.util.Arrays;

public class Field {
    private final char[][] field;

    public Field() {
        this.field = new char[10][10];
        for (int i = 0; i < 10; i++) {
            Arrays.fill(field[i], '~');
        }
    }

    public char getSymbol(Integer[] position) {
        int r = position[0];
        int c = position[1];
        return field[r][c];
    }

    public void setSymbol(Integer[] position, char ch) {
        int r = position[0];
        int c = position[1];
        field[r][c] = ch;
    }

    public void setField(Integer[][] coo) {
        int startR = coo[0][0];
        int endR = coo[1][0];
        int startC = coo[0][1];
        int endC = coo[1][1];
        for (int i = startR; i <= endR; i++) {
            for (int j = startC; j <= endC; j++) {
                field[i][j] = 'O';
            }
        }
    }

    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            char row = (char) ('A' + i);
            System.out.print(String.valueOf(row) + ' ');
            for (int j = 0; j < 10; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void printFogField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            char row = (char) ('A' + i);
            System.out.print(String.valueOf(row) + ' ');
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 'X' || field[i][j] == 'M') {
                    System.out.print(field[i][j] + " ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.print("\n");
        }
    }

    public boolean checkFail() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}
