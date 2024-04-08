public class Player extends GameEntity {
    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return '@';
    }

    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }
}
