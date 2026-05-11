public class Cradle extends Tool {
    public Cradle() {
        super(100);
    }

    @Override
    public int useTool() {
        int maxIncome = 60;
        return maxIncome;
    }
}