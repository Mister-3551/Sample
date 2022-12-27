package core.inventoryscreen;

import com.badlogic.gdx.utils.Json;
import core.API;
import core.ApiResponse;
import core.GameData;
import core.inventoryscreen.objects.Unit;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class InventoryConnection {

    public InventoryConnection() {}

    public ArrayList<Unit> inventoryList() {
        ArrayList<Unit> units = new ArrayList<>();
        try {
            RequestBody formBody = new FormBody.Builder().build();
            String response = ApiResponse.getResponse(API.API_GET_INVENTORY, formBody);
            if (!response.isBlank()) return new Json().fromJson(ArrayList.class, Unit.class, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return units;
    }
}