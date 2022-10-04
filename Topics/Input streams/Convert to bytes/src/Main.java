import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        byte[] bytes = new byte[50];
        int byteRead = inputStream.read(bytes);
        for (int i=0; i<byteRead; i++) System.out.print(bytes[i]);

        inputStream.close();
    }
}