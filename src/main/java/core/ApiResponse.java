package core;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class ApiResponse {

    public static String getResponse(URL url) throws Exception {
        HttpURLConnection conn;
        int responseCode = 0;
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
