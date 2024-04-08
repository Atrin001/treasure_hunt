public class Wall extends GameEntity {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return '#'; // Symbol for the wall
    }
}
