package Games.src.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Games.src.main.GameProgress.saveGame;
import static Games.src.main.GameProgress.zipFiles;


public class Main {
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


        GameProgress gameProgress1=new GameProgress(10,2,1,2);
        GameProgress gameProgress2=new GameProgress(11,4,2,3.1 );
        GameProgress gameProgress3=new GameProgress(13,5,3,4.4);
        saveGame("Games/savegames/save.dat",gameProgress1);
        saveGame("Games/savegames/save2.dat",gameProgress2);
        saveGame("Games/savegames/save3.dat",gameProgress3);

        zipFiles("Games/savegames/save.zip",
                new ArrayList<>(List.of("Games/savegames/save.dat",
                        "Games/savegames/save2.dat",
                        "Games/savegames/save3.dat")));


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
    public static void logMessage(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        log.append("[").append(timestamp).append("] ").append(message).append("\n");
    }


}
