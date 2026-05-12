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