public class Pan extends Tool {
    public Pan() {
        super(100);
    }

    @Override
    public int useTool() {
        int income = randomBetween(0, 60);
        System.out.printf("Pan: earned $%d%n", income);
        return income;
    }
}