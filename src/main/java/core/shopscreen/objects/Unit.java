package core.shopscreen.objects;

public class Unit {

    private String name;
    private int healthPoints;

    public Unit(String name, int healthPoints) {
        this.name = name;
        this.healthPoints = healthPoints;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
