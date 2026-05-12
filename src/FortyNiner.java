import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class FortyNiner {
    private int endurance;
    private int money;
    private ArrayList<Tool> tools;
    private Random rnd;

    public FortyNiner() {
        this(100, 100, createStartingTools());
    }

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
        if (endurance == 0) {
            System.out.printf("Endurance is at 0%%. No mining income this week. Money stays at $%d.%n", money);
            return 0;
        }

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

    public void itIsSundayAgain(Scanner scanner) {
        Sluice sluice = findSluice();

        System.out.printf(
            "Sunday status: endurance %d%%, money $%d%n",
            endurance,
            money
        );
        boolean actionTaken = false;
        while (!actionTaken) {
            System.out.println("Choose your Sunday action:");
            System.out.println("1 - Do nothing");
            System.out.println("2 - Repair a broken sluice for $100");
            System.out.println("3 - Visit the saloon");

            int choice = readNumberInRange(scanner, 1, 3);
            if (choice == 1) {
                System.out.println("You stayed put and saved your money.");
                actionTaken = true;
            } else if (choice == 2) {
                boolean sluiceUsable = sluice != null && sluice.getDurability() > 0;
                fixSluice();
                if (!sluiceUsable) {
                    actionTaken = true;
                }
            } else {
                goToSaloon();
                actionTaken = true;
            }
        }
    }

    private boolean goToSaloon() {
        int cost = rnd.nextInt(151) + 50;
        int recoveredEndurance = rnd.nextInt(46) + 5;
        if (money < cost) {
            System.out.printf("You need $%d for the saloon, but only have $%d.%n", cost, money);
            return false;
        }

        money -= cost;
                endurance = Math.min(100, endurance + recoveredEndurance);
                System.out.printf(
            "The saloon cost you $%d and restored %d%% endurance. Endurance: %d%%, money: $%d.%n",
            cost,
            recoveredEndurance,
            endurance,
            money
        );
        return true;
    }

    private boolean fixSluice() {
        Sluice sluice = findSluice();
        if (sluice == null) {
            System.out.println("You do not own a sluice to repair.");
            return false;
        }
        if (sluice.getDurability() > 0) {
            System.out.printf(
                "Your sluice is still usable at %d%% durability. No repair was needed.%n",
                sluice.getDurability()
            );
            return false;
        }
        if (money < 100) {
            System.out.printf("Repair costs $100, but you only have $%d.%n", money);
            return false;
        }

        money -= 100;
        sluice.repair();
        System.out.printf("You paid $100 for repairs. Money left: $%d.%n", money);
        return true;
    }

    private Sluice findSluice() {
        for (Tool tool : tools) {
            if (tool instanceof Sluice) {
                return (Sluice) tool;
            }
        }
        return null;
    }

    private int readNumberInRange(Scanner scanner, int min, int max) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Keep prompting below.
            }
            System.out.printf("Enter a whole number between %d and %d.%n", min, max);
        }
    }

    private static ArrayList<Tool> createStartingTools() {
        ArrayList<Tool> startingTools = new ArrayList<>();
        startingTools.add(new Pan());
        startingTools.add(new Sluice());
        startingTools.add(new Cradle());
        return startingTools;
    }
}