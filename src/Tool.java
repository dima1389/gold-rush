import java.util.Random;

public abstract class Tool {
    private int durability;
    private Random rnd;

    protected Tool(int durability) {
        this.durability = durability;
        this.rnd = new Random();
    }

    public int getDurability() {
        return durability;
    }

    protected int randomBetween(int min, int max) {
        return rnd.nextInt(max - min + 1) + min;
    }

    public abstract int useTool();
}