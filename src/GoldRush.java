import java.io.File;
import java.util.Scanner;

public class GoldRush {
    private FortyNiner fortyNiner;
    private File savedGame;
    private int currentWeek;

    public GoldRush() {
    }

    public void survive() {
        fortyNiner = new FortyNiner();
        currentWeek = 1;

        Scanner scanner = new Scanner(System.in);
        while (currentWeek <= 20) {
            System.out.printf("%n========== Week %d ==========%n", currentWeek);
            System.out.println("\n--- Sunday ---");
            fortyNiner.itIsSundayAgain(scanner);
            System.out.println("\n--- Cradle Purchase ---");
            fortyNiner.buyCradles(scanner);
            System.out.println("\n--- Mining ---");
            fortyNiner.useTools();
            System.out.println("\n--- Expenses & Losses ---");

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