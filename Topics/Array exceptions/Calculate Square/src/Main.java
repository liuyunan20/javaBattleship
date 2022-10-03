class FixingExceptions {

    public static void calculateSquare(int[] array, int index) {
        if (array != null && index >= 0 && index < array.length) {
            System.out.println(array[index] * array[index]);
        } else {
            System.out.println("Exception!");
        }

    }
}