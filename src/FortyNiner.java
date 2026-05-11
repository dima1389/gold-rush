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

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Tool> getTools() {
        return new ArrayList<>(tools);
    }

    public void setTools(ArrayList<Tool> tools) {
        this.tools = new ArrayList<>(tools);
    }

    public Random getRnd() {
        return rnd;
    }

    public void setRnd(Random rnd) {
        if (rnd != null) {
            this.rnd = rnd;
        }
    }
}