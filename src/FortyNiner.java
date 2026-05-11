import java.util.ArrayList;
import java.util.Random;

public class FortyNiner {
    private int endurance;
    private int money;
    private ArrayList<Tool> tools;
    private Random rnd;

    public FortyNiner(int endurance, int money, ArrayList<Tool> tools) {
        this.endurance = endurance;
        this.money = money;
        this.tools = new ArrayList<>(tools);
        this.rnd = new Random();
    }
}