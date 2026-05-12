public class Cradle extends Tool {
    public Cradle() {
        super(100);
    }

    public Cradle(int durability) {
        super(durability);
    }

    @Override
    public int useTool() {
        if (getDurability() == 0) {
            System.out.println("Cradle: cannot be used because durability is 0%.");
            return 0;
        }

        int income = randomBetween(0, 30);
        if (getRnd().nextInt(100) < 20) {
            setDurability(0);
            System.out.printf("Cradle: earned $%d. It broke during use and durability is now %d%%%n", income, getDurability());
        } else {
            System.out.printf("Cradle: earned $%d. Durability remains at %d%%%n", income, getDurability());
        }

        return income;
    }
}