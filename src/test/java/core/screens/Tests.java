package core.screens;

import com.badlogic.gdx.physics.box2d.Body;
import core.API;
import core.gamescreen.DetectionSystem;
import core.gamescreen.objects.player.Player;
import okhttp3.*;
import org.junit.Test;

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
}