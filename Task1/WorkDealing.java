package Task1;

import java.util.concurrent.Callable;

class WorkDealing implements Callable<Integer> {
    private final int[] row;
    private final int thresholdValue;

    public WorkDealing(int[] row, int thresholdValue) {
        this.row = row;
        this.thresholdValue = thresholdValue;
    }

    @Override
    public Integer call() {
        int min = Integer.MAX_VALUE;
        for (int value : row) {
            if (value >= thresholdValue && value < min) {
                min = value;
            }
        }
        return min;
    }
}

