import java.util.ArrayList;
import java.util.Iterator;
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

    public int useTools() {
        int weeklyIncome = 0;
        Iterator<Tool> iterator = tools.iterator();
        while (iterator.hasNext()) {
            Tool tool = iterator.next();
            weeklyIncome += tool.useTool();
            if (tool instanceof Cradle && tool.getDurability() == 0) {
                iterator.remove();
            }
        }

        money += weeklyIncome;
        System.out.printf("Weekly earnings total: $%d. You now have $%d.%n", weeklyIncome, money);
        return weeklyIncome;
    }

    public int buyFood() {
        int foodCost = rnd.nextInt(21) + 30;
        money -= foodCost;
        System.out.printf("You bought food for $%d. Money left: $%d.%n", foodCost, money);
        return foodCost;
    }

    public int loseEndurance() {
        if (endurance == 0) {
            System.out.println("Your endurance is still 0%.");
            return 0;
        }
        int enduranceLoss = rnd.nextInt(16) + 10;
        endurance = Math.max(0, endurance - enduranceLoss);
        System.out.printf("You lost %d%% endurance. Current endurance: %d%%%n", enduranceLoss, endurance);
        return enduranceLoss;
    }
}