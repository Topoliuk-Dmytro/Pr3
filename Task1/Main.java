package Task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    static int ARRAY_SIZE;
    public static void main(String[] args) {
        menu();

        ArrayMaker arrayMaker = new ArrayMaker(ARRAY_SIZE);
        int[][] array = arrayMaker.generate2DArray();

        //Work stealing
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        WorkStealing task = new WorkStealing(array, 0, arrayMaker.getArrayRows(), array[0][0]*2);
        int minValueWorkStealing = pool.invoke(task);
        long endTime = System.currentTimeMillis();

        if (minValueWorkStealing == Integer.MAX_VALUE)
            System.out.println("\nThe minimum element that is twice more than: " + array[0][0] + " was not found");
        else
            System.out.println("\nThe minimum element that is twice more than: " + array[0][0] + " is " + minValueWorkStealing);

        System.out.println("Work stealing running time - " + (endTime - startTime) + "ms");


        //Work dealing
        startTime = System.currentTimeMillis();
        int minValueWorkDealing = workDealing(array, array[0][0] * 2);
        endTime = System.currentTimeMillis();

        if (minValueWorkDealing == Integer.MAX_VALUE)
            System.out.println("\nThe minimum element that is twice more than: " + array[0][0] + " was not found");
        else
            System.out.println("\nThe minimum element that is twice more than: " + array[0][0] + " is " + minValueWorkDealing);

        System.out.println("Work dealing running time - " + (endTime - startTime) + "ms");

    }

    public static int workDealing(int[][] array, int thresholdValue) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futures = new ArrayList<>();

        for (int[] line : array) {
            futures.add(executor.submit(new WorkDealing(line, thresholdValue)));
        }

        int minValue = Integer.MAX_VALUE;
        try {
            for (Future<Integer> future : futures) {
                minValue = Math.min(minValue, future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return minValue;
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the number of array elements: ");
            ARRAY_SIZE = scanner.nextInt();
            ARRAY_SIZE = Math.max(ARRAY_SIZE, 500);

            System.out.println("\nArray of " + ARRAY_SIZE + " elements in length being processed: ");
        } catch (Exception e) {
            System.out.println("Invalid value!");
            menu();
        }
    }
}