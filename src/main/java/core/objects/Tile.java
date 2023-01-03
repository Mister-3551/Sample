package core.objects;

public class Tile {

    private Long id;
    private String name;
    private int size;

    public Tile() {}

    public Tile(Long id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}