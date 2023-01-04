package core.objects;

public class Skin {

    private int id;
    private String name;
    private String picture;

    public Skin() {}

    public Skin(int id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}