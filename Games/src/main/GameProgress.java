package Games.src.main;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static Games.src.main.Main.logMessage;


public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        File saveFile = new File(path);

        try (FileOutputStream fos = new FileOutputStream(saveFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            logMessage("Создано сохранение: " + saveFile.getAbsolutePath());

        } catch (IOException e) {
            logMessage("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static void zipFiles(String archivePath, List<String> filesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archivePath))) {
            File filepath = null;
            for (String filePath : filesToZip) {
                File file = new File(filePath);
                filepath= file;
                if (!file.exists()) {
                    logMessage("Файл не найден: " + filePath);
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) > 0) {
                        zout.write(buffer, 0, bytesRead);
                    }

                    zout.closeEntry();
                    logMessage("Файл добавлен в архив: " + filePath);

                }

            }
            logMessage("Создан Zip Архив: " + archivePath);
            deleteFilesExceptZip(filepath.getParent());
        } catch (IOException e) {
            logMessage("Ошибка при создании архива: " + e.getMessage());
        }
    }

    public static void deleteFilesExceptZip(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists() || !directory.isDirectory()) {
            logMessage("Директория не найдена: " + dirPath);
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (!file.getName().endsWith(".zip")) {
                file.delete();
                logMessage("Удален файл "+file.getName());
            }
        }

    }
}