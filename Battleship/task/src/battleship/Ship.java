package battleship;

import java.util.ArrayList;

public class Ship {
    String name;
    int length;

    ArrayList<Integer[]> coordinates;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public ArrayList<Integer[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Integer[][] coo) {
        this.coordinates = new ArrayList<>();
        int startR = coo[0][0];
        int endR = coo[1][0];
        int startC = coo[0][1];
        int endC = coo[1][1];
        for (int i = startR; i <= endR; i++) {
            for (int j = startC; j <= endC; j++) {
                coordinates.add(new Integer[] {i, j});
            }
        }
    }

    public boolean checkSank(Field field) {
            for (Integer[] position: coordinates) {
                if (field.getSymbol(position) == 'O') {
                    return false;
                }
            }
        return true;
    }
}
