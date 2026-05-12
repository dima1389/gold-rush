import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoldRush {
    private FortyNiner fortyNiner;
    private File savedGame;
    private int currentWeek;

    public GoldRush() {
        savedGame = new File("saved-game.txt");
    }

    public void survive() {
        if (fortyNiner == null) {
            fortyNiner = new FortyNiner();
            currentWeek = 1;
        }

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
        if (!savedGame.exists()) {
            resetGameState();
            System.out.println("No saved game found. Starting a new game.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(savedGame.toPath(), StandardCharsets.UTF_8);
            if (lines.size() < 4) {
                resetGameState();
                System.out.println("Saved game file is incomplete. Starting a new game instead.");
                return;
            }

            int loadedWeek = parseValue(lines.get(0), "Week no. ", "");
            int endurance = parseValue(lines.get(1), "49er endurance: ", "%");
            int money = parseValue(lines.get(2), "49er money: $", "");
            int sluiceDurability = parseValue(lines.get(3), "Sluice durability: ", "%");

            ArrayList<Tool> loadedTools = new ArrayList<>();
            loadedTools.add(new Pan());
            loadedTools.add(new Sluice(sluiceDurability));

            for (int i = 4; i < lines.size(); i++) {
                String line = lines.get(i);
                if (!line.startsWith("Cradle durability: ")) {
                    throw new NumberFormatException("Unexpected save line: " + line);
                }

                int cradleDurability = parseValue(line, "Cradle durability: ", "%");
                if (cradleDurability > 0) {
                    loadedTools.add(new Cradle(cradleDurability));
                }
            }

            int nextWeek = Math.max(1, loadedWeek);
            if (nextWeek > 20) {
                System.out.println("The saved game was already completed. Starting a new game.");
                resetGameState();
                if (!savedGame.delete()) {
                    System.out.println("Finished save file could not be deleted automatically.");
                }
                return;
            }

            fortyNiner = new FortyNiner(endurance, money, loadedTools);
            currentWeek = nextWeek;
            System.out.printf("Loaded saved game. Continuing from week %d.%n", currentWeek);
        } catch (IOException | NumberFormatException ex) {
            System.out.printf("Could not load saved game (%s). Starting a new game.%n", ex.getMessage());
            resetGameState();
        }
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

    private int parseValue(String line, String prefix, String suffix) {
        if (!line.startsWith(prefix) || !line.endsWith(suffix)) {
            throw new NumberFormatException("Unexpected save format: " + line);
        }

        int start = prefix.length();
        int end = line.length() - suffix.length();
        if (start > end) {
            throw new NumberFormatException("Missing value in save line: " + line);
        }

        String value = line.substring(start, end).trim();
        return Integer.parseInt(value);
    }

    private void resetGameState() {
        fortyNiner = null;
        currentWeek = 1;
    }
}