package core.downloadfile;

import core.API;
import core.GameData;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadFile {

    private static final String levelPictureUrl = API.LEVEL_PICTURE;
    private static final String welcomePictureUrl = "";
    private static final String tiles70X70Url = API.TILES_PICTURE_70X70;
    private static final String levelMapsUrl = API.LEVEL_MAP;
    private static final String skinUrl = API.SKIN_PICTURE;
    private static final String gameNameFolder = "GameName";
    private static final String assetsFolder = "assets";
    private static final String picturesFolder = "pictures";
    private static final String levelPicturesFolder = "level-pictures";
    private static final String ownedSkinsPicturesFolder = "skin-pictures";
    private static final String welcomePicturesFolder = "welcome-picture";
    private static final String levelMapsFolder = "level-map";
    private static final String tilesFolder = "tiles";
    private static final String tilesDimensionsFolder = "tiles-dimensions";
    private static final String tiles70X70Folder = "70x70";
    private static final String temporaryFolder = "temporary";
    private static final String skinsFolder = "skins";
    private static final String playerFolder = "player";
    private static final String enemyFolder = "enemy";
    private static final String hostageFolder = "hostage";
    private static final String basicPath = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + assetsFolder + "/";
    private static final String basicTemporaryPath = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + temporaryFolder + "/";
    private static final String levelPicturesDirectory = basicPath + picturesFolder + "/" + levelPicturesFolder + "/";
    private static final String levelMapsDirectory =  basicTemporaryPath + levelMapsFolder + "/";
    private static final String tiles70X70Directory = basicPath + tilesFolder + "/" + tilesDimensionsFolder + "/" + tiles70X70Folder + "/";
    private static final String skinsTemporaryDirectory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + temporaryFolder + "/" + skinsFolder + "/";
    private static final String ownedSkinsDirectory = basicPath + picturesFolder + "/" + ownedSkinsPicturesFolder + "/";

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

    public static String getTemporarySkin(String character, String type) {
        checkDirectories();
        return Skin.saveTemporarySkin(character, type).replace("\\", "/");
    }

    public static String getOwnedSkins(String character, String type) {
        checkDirectories();
        return Skin.saveOwnedSkin(character, type).replace("\\", "/");
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
                Files.copy(new URL(levelMapsUrl + "/" + mapName).openStream(), Paths.get(levelMapsDirectory + GameData.Directory.TEMPORARY_MAP_NAME), StandardCopyOption.REPLACE_EXISTING); //3 - StandardCopyOption.REPLACE_EXISTING
                return levelMapsDirectory;
            } catch (Exception e) {
                return levelMapsDirectory;
            }
        }
    }

    private static class Skin {
        private static String saveTemporarySkin(String character, String type) {
            try {
                String skinDirectory = createPlayerSkinsDirectory(character);
                Files.copy(new URL(skinUrl + "/" + character + "/" + type + "/" + character + "-" + type + "-stand.png").openStream(), Paths.get(skinDirectory + "/" + character + "-stand.png"), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(new URL(skinUrl + "/" + character + "/" + type + "/" + character + "-" + type + "-left.png").openStream(), Paths.get(skinDirectory + "/" + character + "-left.png") , StandardCopyOption.REPLACE_EXISTING);
                Files.copy(new URL(skinUrl + "/" + character + "/" + type + "/" + character + "-" + type + "-right.png").openStream(), Paths.get( skinDirectory + "/" + character + "-right.png"), StandardCopyOption.REPLACE_EXISTING);
                return skinDirectory;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "";
            }
        }

        private static String saveOwnedSkin(String character, String type) {
            try {
                Files.copy(new URL(skinUrl + "/" + character + "/" + type.split("-")[1] + "/" + type + "-stand.png").openStream(), Paths.get(ownedSkinsDirectory + "/" + type + ".png"));
                return ownedSkinsDirectory;
            } catch (Exception e) {
                if (!e.getMessage().matches("java.nio.file.FileAlreadyExistsException")) return ownedSkinsDirectory;
                System.out.println(e.getMessage());
                return "";
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

        File ownedSkinsPicturesDirectory = new File(basicPath + "/" + picturesFolder, ownedSkinsPicturesFolder);
        checkIfDirectoryExists(ownedSkinsPicturesDirectory);

        File welcomePicturesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + temporaryFolder, welcomePicturesFolder);
        checkIfDirectoryExists(welcomePicturesDirectory);

        File tilesDirectory = new File(basicPath, tilesFolder);
        checkIfDirectoryExists(tilesDirectory);

        File tilesDimensionsDirectory = new File(basicPath + "/" + tilesFolder, tilesDimensionsFolder);
        checkIfDirectoryExists(tilesDimensionsDirectory);

        File tiles70X70Directory = new File(basicPath + "/" + tilesFolder + "/" + tilesDimensionsFolder, tiles70X70Folder);
        checkIfDirectoryExists(tiles70X70Directory);

        File temporaryDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder, temporaryFolder);
        checkIfDirectoryExists(temporaryDirectory);

        File levelMapsDirectory = new File(basicTemporaryPath, levelMapsFolder);
        checkIfDirectoryExists(levelMapsDirectory);

        File skinsDirectory = new File(basicTemporaryPath, skinsFolder);
        checkIfDirectoryExists(skinsDirectory);

        File playerDirectory = new File(basicTemporaryPath + "/" + skinsFolder, playerFolder);
        checkIfDirectoryExists(playerDirectory);

        File enemyDirectory = new File(basicTemporaryPath + "/" + skinsFolder, enemyFolder);
        checkIfDirectoryExists(enemyDirectory);

        File hostageDirectory = new File(basicTemporaryPath + skinsFolder, hostageFolder);
        checkIfDirectoryExists(hostageDirectory);
    }

    private static String createPlayerSkinsDirectory(String directoryName) {
        File directoryFolder = new File(skinsTemporaryDirectory, directoryName);
        checkIfDirectoryExists(directoryFolder);
        return directoryFolder.getAbsolutePath();
    }

    private static void checkIfDirectoryExists(File directory) {
        if (!directory.exists()) directory.mkdirs();
    }
}