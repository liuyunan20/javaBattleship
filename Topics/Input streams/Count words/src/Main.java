import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String words = reader.readLine();
        String[] wordArray = words.strip().split("\\s+");
        if (words.matches("\\s+")) {
            System.out.print(0);
        } else {
            System.out.print(wordArray.length);
        }
        reader.close();
    }
}