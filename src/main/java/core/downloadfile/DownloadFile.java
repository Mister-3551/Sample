package core.downloadfile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;

public class DownloadFile {

    private static final String levelPicturesUrl = "http://192.168.1.171:8079/level-picture/";
    private static String gameNameMap = "GameName";
    private static String assetsMap = "assets";
    private static String levelPicturesMap = "level-picture";
    private static String levelMapMap = "level-map";
    private static final String levelPicturesDirectory = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameMap + "/" + assetsMap + "/" + levelPicturesMap;

    public static String getFile(String filename) throws Exception {
        return responseFile(levelPicturesUrl, filename);
    }

    public static String responseFile(String givenUrl, String filename) throws Exception {

        String url = givenUrl + filename;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) return "Failed to download file";

        checkFileSaveDirectory();
        return saveFile(response, filename);
    }

    private static String saveFile(Response response, String filename) throws Exception {
        FileOutputStream fos = new FileOutputStream(levelPicturesDirectory + "/" + filename);
        fos.write(response.body().bytes());
        fos.close();
        return levelPicturesDirectory;
    }

    public static void checkFileSaveDirectory() {
        File mainDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), gameNameMap);
        if (!mainDirectory.exists()) mainDirectory.mkdirs();

        File assetsDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameMap, assetsMap);
        if (!assetsDirectory.exists()) assetsDirectory.mkdirs();

        File levelPicturesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameMap + "/" + assetsMap, levelPicturesMap);
        if (!levelPicturesDirectory.exists()) levelPicturesDirectory.mkdirs();

        File levelMapsDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameMap + "/" + assetsMap, levelMapMap);
        if (!levelMapsDirectory.exists()) levelMapsDirectory.mkdirs();
    }
}