package Task2;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the directory path: ");
        String directoryPath = scanner.nextLine();

        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return;
        }

        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        FileProcessor task = new FileProcessor(directory);
        pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("\nExecution time: " + (endTime - startTime) + " ms");
    }
}
