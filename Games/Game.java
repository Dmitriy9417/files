package Games;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game {
    private static final StringBuilder log = new StringBuilder();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        createDir("Games/src");
        createDir("Games/res");
        createDir("Games/savegames");
        createDir("Games/temp");
        createDir("Games/src/main");
        createDir("Games/src/test");
        createFile("Games/src/main/", "Main.java");
        createFile("Games/src/main/", "Utils.java");
        createDir("Games/res/drawables");
        createDir("Games/res/vectors");
        createDir("Games/res/icons");
        createFile("Games/temp", "temp.txt");
        saveLogToFile();
    }

    private static void createFile(String dir, String file) {

        File create = new File(dir, file);
        try {
            if (create.createNewFile()) {
                logMessage("Файл создан: " + create.getAbsolutePath());
            } else {
                logMessage("Файл уже существует: " + create.getAbsolutePath());
            }
        } catch (IOException e) {
            logMessage("Ошибка при создании файла: " + create.getAbsolutePath() + " " + e + create.getAbsolutePath());
        }

    }

    private static void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            if (file.mkdir()) {
                logMessage("Директория создана " + file.getAbsolutePath());
            } else {
                logMessage("Директория не создана " + file.getAbsolutePath());
            }
        } else {
            logMessage("Директория уже существует " + file.getAbsolutePath());
        }
    }

    private static void saveLogToFile() {
        File logFile = new File("Games/temp", "temp.txt");
        try (FileWriter writer = new FileWriter(logFile)) {
            logMessage("Удачная запись лога");
            writer.write(log.toString());
        } catch (IOException e) {
            logMessage("Ошибка записи лога в файл: " + e.getMessage());
        }
    }
    private static void logMessage(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        log.append("[").append(timestamp).append("] ").append(message).append("\n");
    }


}
