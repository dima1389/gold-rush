import java.io.File;

public class GoldRush {
    private FortyNiner fortyNiner;
    private File savedGame;
    private int currentWeek;

    public GoldRush() {
    }

    public void survive() {
        fortyNiner = new FortyNiner();
        currentWeek = 1;

        while (currentWeek <= 20) {
            System.out.printf("%n========== Week %d ==========%n", currentWeek);
            System.out.println("Sunday");
            System.out.println("Mining");
            fortyNiner.useTools();
            System.out.println("Expenses & Losses");
            fortyNiner.buyFood();
            fortyNiner.loseEndurance();
            System.out.printf(
                "End of week %d: endurance %d%%, money $%d.%n",
                currentWeek,
                fortyNiner.getEndurance(),
                fortyNiner.getMoney()
            );

            currentWeek++;
        }

        System.out.printf("%nAll 20 weeks are over. Final money: $%d.%n", fortyNiner.getMoney());
    }

    public void loadGame() {
    }

    private void saveGame() {
    }
}