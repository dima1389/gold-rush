import java.util.Random;

public abstract class Tool {
    private int durability;
    private Random rnd;

    protected Tool(int durability) {
        this.durability = durability;
        this.rnd = new Random();
    }
}