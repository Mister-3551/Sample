package core.settingsscreen.objects;

public class Settings {

    private int keyLeft;
    private int keyRight;
    private int keyJump;
    private int keyShoot;
    private float music;
    private float soundEffect;
    private String language;

    public Settings() {}

    public Settings(int keyLeft, int keyRight, int keyJump, int keyShoot, float music, float soundEffect, String language) {
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyJump = keyJump;
        this.keyShoot = keyShoot;
        this.music = music;
        this.soundEffect = soundEffect;
        this.language = language;
    }

    public int getKeyLeft() {
        return keyLeft;
    }

    public int getKeyRight() {
        return keyRight;
    }

    public int getKeyJump() {
        return keyJump;
    }

    public int getKeyShoot() {
        return keyShoot;
    }

    public float getMusic() {
        return music;
    }

    public float getSoundEffect() {
        return soundEffect;
    }

    public String getLanguage() {
        return language;
    }

    public static class Setting {

        private String name;
        private float code;

        public Setting(String name, float code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public float getCode() {
            return code;
        }
    }
}