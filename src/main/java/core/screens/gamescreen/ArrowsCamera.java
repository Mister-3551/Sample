package core.screens.gamescreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ArrowsCamera {

    public static void move(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCameraWithArrows(camera, "left");
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCameraWithArrows(camera, "right");
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCameraWithArrows(camera, "up");
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCameraWithArrows(camera, "down");

        /*var playerPositionX = Math.abs(player.getBody().getPosition().x * GameData.PPM * 10) / 10f;
            var playerPositionY = Math.abs(player.getBody().getPosition().y * GameData.PPM * 10) / 10f;
            var cameraMovingPositionX = 660;
            var cameraMovingPositionY = 400;
            var mapWidth = GameData.MAP_WIDTH * 64;
            Vector3 position = camera.position;
            position.x = playerPositionX;
            position.y = playerPositionY;
        if (!GameData.GameScreen.Camera.RESET_CAMERA_POSITION) {
            if (playerPositionX > cameraMovingPositionX) position.x = playerPositionX;
            else if (playerPositionX > mapWidth - GameData.SCREEN_WIDTH / 2) position.x = playerPositionX;
            else position.x = cameraMovingPositionX;
            if (playerPositionY > cameraMovingPositionY) position.y = playerPositionY;
        } else {
            position.x = playerPositionX;
            if (playerPositionX < cameraMovingPositionX) position.x = cameraMovingPositionX;
            if (playerPositionY < cameraMovingPositionY) position.y = cameraMovingPositionY;
            GameData.GameScreen.Camera.RESET_CAMERA_POSITION = false;
        }
            camera.position.set(position);
            camera.update();
          */
    }

    private static void moveCameraWithArrows(OrthographicCamera camera, String move) {
        GameScreen.moveCameraWithArrows = true;
        int param = 3;
        switch (move) {
            case "left" -> camera.position.x -= param;
            case "right" -> camera.position.x += param;
            case "up" -> camera.position.y += param;
            case "down" -> camera.position.y -= param;
        }
        camera.update();
    }
}