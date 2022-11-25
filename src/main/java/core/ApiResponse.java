package core;

import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.util.Base64;

public class ApiResponse {

    private static HttpURLConnection conn;

    /*public static String getResponse(URL url) throws Exception {
        int responseCode;
        String inline = "";

        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
        }
        conn.disconnect();
        return inline;
    }*/

    public static String getResponse(String url, RequestBody formBody) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        String responseString = response.body().string();

        response.close();
        return responseString;
    }

    public static JSONObject getDataFromGameToken(String response) throws Exception {
        String[] chunks = response.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        JSONParser parse = new JSONParser();
        JSONObject object = (JSONObject) parse.parse(payload);

        return object;
    }
}
