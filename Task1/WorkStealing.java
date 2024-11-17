package Task1;

import java.util.concurrent.RecursiveTask;

class WorkStealing extends RecursiveTask<Integer> {
    private final int[][] matrix;
    private final int startRow, endRow;
    private final int thresholdValue;
    private final int THRESHOLD;

    public WorkStealing(int[][] matrix, int startRow, int endRow, int thresholdValue) {
        this.matrix = matrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.thresholdValue = thresholdValue;

        THRESHOLD = Math.max(matrix.length / Runtime.getRuntime().availableProcessors(), 1);
    }

    @Override
    protected Integer compute() {
        if ((endRow - startRow) <= THRESHOLD) {
            int min = Integer.MAX_VALUE;
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    int value = matrix[i][j];
                    if (value >= thresholdValue && value < min) {
                        min = value;
                    }
                }
            }
            return min;
        } else {
            int mid = (startRow + endRow) / 2;
            WorkStealing leftTask = new WorkStealing(matrix, startRow, mid, thresholdValue);
            WorkStealing rightTask = new WorkStealing(matrix, mid, endRow, thresholdValue);
            invokeAll(leftTask, rightTask);
            return Math.min(leftTask.join(), rightTask.join());
        }
    }
}

