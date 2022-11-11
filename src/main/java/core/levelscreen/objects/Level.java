package core.levelscreen.objects;

public class Level {

    private String name;
    private String map;
    private int completed;

    public Level(String name, String map, int completed) {
        this.name = name;
        this.map = map;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public String getMap() {
        return map;
    }

    public int getCompleted() {
        return completed;
    }
}
