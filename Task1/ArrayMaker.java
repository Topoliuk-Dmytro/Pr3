package Task1;

import java.util.Random;

public class ArrayMaker {
    public int ARRAY_SIZE;
    public int ARRAY_ROWS;

    public ArrayMaker (int ARRAY_SIZE) {
        this.ARRAY_SIZE = ARRAY_SIZE;
    }

    public int[][] generate2DArray() {
        Random random = new Random();
        int rows = (int) Math.sqrt(ARRAY_SIZE);
        ARRAY_ROWS = rows;
        int[][] jaggedArray = new int[rows][];
        int remainingElements = ARRAY_SIZE;

        for (int i = 0; i < rows; i++) {
            int cols = (int) Math.ceil((double) remainingElements / (rows - i));
            jaggedArray[i] = new int[cols];

            for (int j = 0; j < cols; j++) {
                jaggedArray[i][j] = random.nextInt(1000);
            }

            remainingElements -= cols;
        }

        printMatrix(jaggedArray);
        return jaggedArray;
    }

    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    public int getArrayRows() {
        return ARRAY_ROWS;
    }
}
