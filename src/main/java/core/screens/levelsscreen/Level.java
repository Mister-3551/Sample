package core.screens.levelsscreen;

public class Level {

    private Long id;
    private String name;
    private String picture;
    private String map;
    private int completed;

    public Level() {}

    public Level(Long id, String name, String picture, String map, int completed) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.map = map;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getMap() {
        return map;
    }

    public int getCompleted() {
        return completed;
    }
}
