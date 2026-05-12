import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class GoldRush {
    private FortyNiner fortyNiner;
    private File savedGame;
    private int currentWeek;

    public GoldRush() {
        savedGame = new File("saved-game.txt");
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

            if (currentWeek < 20 && shouldSaveAndQuit(scanner)) {
                if (saveGame()) {
                    System.out.println("Game saved. Come back when you want to continue.");
                    return;
                }
                System.out.println("The game could not be saved, so play continues.");
            }

            currentWeek++;
        }

        System.out.printf("%nAll 20 weeks are over. Final money: $%d.%n", fortyNiner.getMoney());
    }

    public void loadGame() {
    }

    private boolean saveGame() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Week no. " + (currentWeek + 1));
        lines.add("49er endurance: " + fortyNiner.getEndurance() + "%");
        lines.add("49er money: $" + fortyNiner.getMoney());
        lines.add("Sluice durability: " + getSluiceDurability() + "%");

        for (Tool tool : fortyNiner.getTools()) {
            if (tool instanceof Cradle && tool.getDurability() > 0) {
                lines.add("Cradle durability: " + tool.getDurability() + "%");
            }
        }

        try {
            Files.write(savedGame.toPath(), lines, StandardCharsets.UTF_8);
            System.out.printf("Game state saved to %s.%n", savedGame.getAbsolutePath());
            return true;
        } catch (IOException ex) {
            System.out.printf("Failed to save the game: %s%n", ex.getMessage());
            return false;
        }
    }

    private boolean shouldSaveAndQuit(Scanner scanner) {
        System.out.println();
        System.out.print("Do you want to save and quit? (y/n): ");
        while (true) {
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.print("Please answer with y or n: ");
        }
    }

    private int getSluiceDurability() {
        for (Tool tool : fortyNiner.getTools()) {
            if (tool instanceof Sluice) {
                return tool.getDurability();
            }
        }
        return 0;
    }
}