public class Sluice extends Tool {
    public Sluice() {
        super(100);
    }

    @Override
    public int useTool() {
        if (getDurability() == 0) {
            System.out.println("Sluice: cannot be used because durability is 0%.");
            return 0;
        }

        int income = randomBetween(0, 500);
        int durabilityLoss = randomBetween(20, 50);
        setDurability(getDurability() - durabilityLoss);

        System.out.printf(
            "Sluice: earned $%d. Durability dropped by %d%% to %d%%%n",
            income,
            durabilityLoss,
            getDurability()
        );
        return income;
    }

    public void repair() {
        setDurability(100);
        System.out.println("Sluice: repaired to 100% durability.");
    }
}