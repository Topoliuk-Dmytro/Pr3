package Task2;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.RecursiveTask;


class FileProcessor extends RecursiveTask<Void> {
    private final File directory;

    public FileProcessor(File directory) {
        this.directory = directory;
    }

    @Override
    protected Void compute() {
        File[] files = directory.listFiles();

        if (files == null) return null;

        for (File file : files) {
            if (file.isDirectory()) {
                FileProcessor subTask = new FileProcessor(file);
                subTask.fork();
            } else if (file.isFile() && file.getName().endsWith(".txt")) {
                processFile(file);
            }
        }

        return null;
    }

    private void processFile(File file) {
        try {
            String content = Files.readString(file.toPath());
            System.out.println("File: " + file.getName() + ", Characters: " + content.length());
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getName());
        }
    }
}