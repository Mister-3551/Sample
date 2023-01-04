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
    private static final String skinsUrl = API.SKIN_PICTURE;
    private static final String gameNameFolder = "GameName";
    private static final String assetsFolder = "assets";
    private static final String picturesFolder = "pictures";
    private static final String levelPicturesFolder = "level-pictures";
    private static final String welcomePicturesFolder = "welcome-picture";
    private static final String levelMapsFolder = "level-maps";
    private static final String tilesFolder = "tiles";
    private static final String tilesDimensionsFolder = "tiles-dimensions";
    private static final String tiles70X70Folder = "70x70";
    private static final String skinsFolder = "skins";
    private static final String playerFolder = "player";
    private static final String enemyFolder = "enemy";
    private static final String hostageFolder = "hostage";
    private static final String basicPath = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/";
    private static final String levelPicturesDirectory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + picturesFolder + "/" + levelPicturesFolder + "/";
    private static final String levelMapsDirectory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + levelMapsFolder + "/";
    private static final String tiles70X70Directory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/" + tilesFolder + "/" + tilesDimensionsFolder + "/" + tiles70X70Folder + "/";
    private static final String skinsDirectory = basicPath + picturesFolder + "/" + skinsFolder + "/";

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

    public static String getSkins(String skinName) {
        checkDirectories();
        return Skin.saveSkin(skinName).replace("\\", "/");
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

    private static class Skin {
        private static String saveSkin(String skinName) {
            try {
                Files.copy(new URL(skinsUrl + "/" + skinName).openStream(), Paths.get(skinsDirectory + skinName)); //3 - StandardCopyOption.REPLACE_EXISTING
                return skinsDirectory;
            } catch (Exception e) {
                return skinsDirectory;
            }
        }
    }

    private static void checkDirectories() {
        File mainDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), gameNameFolder);
        checkIfDirectoryExists(mainDirectory);

        File assetsDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder, assetsFolder);
        checkIfDirectoryExists(assetsDirectory);

        File picturesDirectory = new File(basicPath, picturesFolder);
        checkIfDirectoryExists(picturesDirectory);

        File levelPicturesDirectory = new File(basicPath + "/" + picturesFolder, levelPicturesFolder);
        checkIfDirectoryExists(levelPicturesDirectory);

        File welcomePicturesDirectory = new File(basicPath + "/" + picturesFolder, welcomePicturesFolder);
        checkIfDirectoryExists(welcomePicturesDirectory);

        File levelMapsDirectory = new File(basicPath, levelMapsFolder);
        checkIfDirectoryExists(levelMapsDirectory);

        File tilesDirectory = new File(basicPath, tilesFolder);
        checkIfDirectoryExists(tilesDirectory);

        File tilesDimensionsDirectory = new File(basicPath + "/" + tilesFolder, tilesDimensionsFolder);
        checkIfDirectoryExists(tilesDimensionsDirectory);

        File tiles70X70Directory = new File(basicPath + "/" + tilesFolder + "/" + tilesDimensionsFolder, tiles70X70Folder);
        checkIfDirectoryExists(tiles70X70Directory);

        File skinsDirectory = new File(basicPath + "/" + picturesFolder, skinsFolder);
        checkIfDirectoryExists(skinsDirectory);

        File playerDirectory = new File(basicPath + "/" + picturesFolder + "/" + skinsFolder, playerFolder);
        checkIfDirectoryExists(playerDirectory);

        File enemyDirectory = new File(basicPath + "/" + picturesFolder + "/" + skinsFolder, enemyFolder);
        checkIfDirectoryExists(enemyDirectory);

        File hostageDirectory = new File(basicPath + "/" + picturesFolder + "/" + skinsFolder, hostageFolder);
        checkIfDirectoryExists(hostageDirectory);
    }

    private static void checkIfDirectoryExists(File directory) {
        if (!directory.exists()) directory.mkdirs();
    }
}