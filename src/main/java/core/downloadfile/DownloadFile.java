package core.downloadfile;

import core.API;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadFile {

    private static final String levelPictureUrl = API.LEVEL_PICTURE;
    private static final String welcomePictureUrl = "";
    private static final String tiles70X70Url = API.TILES_PICTURE_70X70;
    private static final String levelMapsUrl = API.LEVEL_MAP;
    private static final String gameNameFolder = "GameName";
    private static final String assetsFolder = "assets";
    private static final String picturesFolder = "pictures";
    private static final String levelPicturesFolder = "level-pictures";
    private static final String welcomePicturesFolder = "welcome-picture";
    private static final String levelMapsFolder = "level-maps";
    private static final String tilesFolder = "tiles";
    private static final String tilesDimensionsFolder = "tiles-dimensions";
    private static final String tiles70X70Folder = "70x70";
    private static final String levelPicturesDirectory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + picturesFolder + "/" + levelPicturesFolder + "/";
    private static final String levelMapsDirectory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + levelMapsFolder + "/";
    private static final String tiles70X70Directory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + tilesFolder + "/" + tilesDimensionsFolder + "/" + tiles70X70Folder + "/";

    public static String getLevelPicture(String levelName) {
        checkDirectories();
        return Picture.savePicture(levelName).replace("\\", "/");
    }

    public static String getWelcomePicture(String welcomeName) {
        checkDirectories();
        return Picture.savePicture(welcomeName).replace("\\", "/");
    }

    public static String getTiles70X70Pictures(String tileName) {
        checkDirectories();
        return Tile.saveTiles(tileName).replace("\\", "/");
    }

    public static String getLevelMaps(String mapName) {
        checkDirectories();
        return Map.saveMap(mapName);
    }

    private static class Picture {
        private static String savePicture(String levelName) {
            try {
                Files.copy(new URL(levelPictureUrl + "/" + levelName).openStream(), Paths.get(levelPicturesDirectory + levelName)); //3 - StandardCopyOption.REPLACE_EXISTING
                return levelPicturesDirectory;
            } catch (Exception e) {
                return levelPicturesDirectory;
            }
        }

    }

    private static class Tile {
        private static String saveTiles(String tileName) {
            try {
                Files.copy(new URL(tiles70X70Url + "/" + tileName).openStream(), Paths.get(tiles70X70Directory + tileName)); //3 - StandardCopyOption.REPLACE_EXISTING
                return tiles70X70Directory;
            } catch (Exception e) {
                return tiles70X70Directory;
            }
        }
    }

    private static class Map {
        private static String saveMap(String mapName) {
            try {
                Files.copy(new URL(levelMapsUrl + "/" + mapName).openStream(), Paths.get(levelMapsDirectory + mapName)); //3 - StandardCopyOption.REPLACE_EXISTING
                return levelMapsDirectory;
            } catch (Exception e) {
                return levelMapsDirectory;
            }
        }
    }

    private static void checkDirectories() {
        File mainDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), gameNameFolder);
        if (!mainDirectory.exists()) mainDirectory.mkdirs();

        File assetsDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder, assetsFolder);
        if (!assetsDirectory.exists()) assetsDirectory.mkdirs();

        File picturesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder, picturesFolder);
        if (!picturesDirectory.exists()) picturesDirectory.mkdirs();

        File levelPicturesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + picturesFolder, levelPicturesFolder);
        if (!levelPicturesDirectory.exists()) levelPicturesDirectory.mkdirs();

        File welcomePicturesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + picturesFolder, welcomePicturesFolder);
        if (!welcomePicturesDirectory.exists()) welcomePicturesDirectory.mkdirs();

        File levelMapsDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder, levelMapsFolder);
        if (!levelMapsDirectory.exists()) levelMapsDirectory.mkdirs();

        File tilesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder, tilesFolder);
        if (!tilesDirectory.exists()) tilesDirectory.mkdirs();

        File tilesDimensionsDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + tilesFolder, tilesDimensionsFolder);
        if (!tilesDimensionsDirectory.exists()) tilesDimensionsDirectory.mkdirs();

        File tiles70X70Directory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + tilesFolder + "/" + tilesDimensionsFolder, tiles70X70Folder);
        if (!tiles70X70Directory.exists()) tiles70X70Directory.mkdirs();
    }
}