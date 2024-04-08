public class Trap extends GameEntity {
    public Trap(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'X'; // Symbol for the trap
    }
}
