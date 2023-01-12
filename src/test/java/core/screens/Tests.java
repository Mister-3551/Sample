package core.screens;

import core.API;
import core.GameData;
import core.ResponseDataConnection;
import okhttp3.*;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;

public class Tests {

    @Test
    public void apiConnection() throws Exception{

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "urban4")
                .add("password", "qwqwqwqw")
                .build();

        Request request = new Request.Builder()
                .url(API.HOST + "/sign-in")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }

    @Test
    public void signIn() throws Exception {
        boolean authentication = ResponseDataConnection.SignInScreen.userAuthentication("", "");
        System.out.println(authentication);
    }

    @Test
    public void getPlayerBasicData() throws Exception {
        GameData.Player.PLAYER_ID = 1L;
        ResponseDataConnection.MenuScreen.getPlayerBasicData();
    }

    @Test
    public void xml() throws Exception {
        System.out.println();
    }

    @Test
    public void path() {
        System.out.println(FileSystemView.getFileSystemView().getDefaultDirectory());
    }
}