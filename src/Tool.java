import java.util.Random;

public abstract class Tool {
    private int durability;
    private Random rnd;

    protected Tool() {
        this(100);
    }

    protected Tool(int durability) {
        this.durability = normalizeDurability(durability);
        this.rnd = new Random();
    }

    public int getDurability() {
        return durability;
    }

    protected void setDurability(int durability) {
        this.durability = normalizeDurability(durability);
    }

    public Random getRnd() {
        return rnd;
    }

    public void setRnd(Random rnd) {
        if (rnd != null) {
            this.rnd = rnd;
        }
    }

    protected int randomBetween(int min, int max) {
        return rnd.nextInt(max - min + 1) + min;
    }

    private int normalizeDurability(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 100) {
            return 100;
        }
        return value;
    }

    public abstract int useTool();
}