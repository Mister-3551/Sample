package core.loginscreen;

import core.ApiResponse;
import core.Constants;
import core.PlayerData;
import core.xml.XmlFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

import static core.API.API_SIGN_IN;

public class LoginConnection {

    private URL url;

    public LoginConnection() {}

    public boolean userAuthentication(String username, String password) {
        try {
            url = new URL(String.format(API_SIGN_IN, username, password));
            String response = ApiResponse.getResponse(url);
            if (!response.isBlank()) {
                setUserData(response);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /*public boolean checkIfUserExists(String username, String password) {
        int responseCode = 0;

        try {
            url = new URL(String.format(API_SIGN_IN, username, password));

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                System.out.println(responseCode);
            } else {
                String gameToken = "";
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    gameToken += scanner.nextLine();
                }
                scanner.close();

                new XmlFile().setUserData(gameToken);
                if (!gameToken.isEmpty()) {
                    setUserData("Name", "1");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }*/

    /*public boolean checkIfUserSignIn(String gameToken) {
        int responseCode = 0;
        if (gameToken.equals("")) return false;
        try {
            url = new URL("http://localhost:8081/gameSignIn/t/" + gameToken);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                System.out.println(responseCode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONObject person = (JSONObject) parse.parse(inline);

                if (!person.isEmpty()) {
                    setUserData(person.get("USERNAME").toString(), person.get("RANK").toString());
                    Constants.GAME_TOKEN = gameToken;
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }*/

    /*public void signOut() {
        int responseCode = 0;
        try {
            url = new URL("http://localhost:8081/gameSignOut/t/" + Constants.GAME_TOKEN);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                System.out.println(responseCode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONObject person = (JSONObject) parse.parse(inline);
                if (person.get("MESSAGE").equals("1")) Constants.GAME_TOKEN = "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/

    private void setUserData(String response) throws Exception {
        var object = ApiResponse.getDataFromGameToken(response);
        PlayerData.PLAYER_NAME = (String) object.get("name");
        PlayerData.PLAYER_USERNAME = (String) object.get("username");
        PlayerData.PLAYER_RANK = (String) object.get("rank");
        PlayerData.PLAYER_GAME_TOKEN = response;
    }
}