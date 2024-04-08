public abstract class GameEntity {
    protected int x;
    protected int y;

    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract char getSymbol(); // This method will return the character symbol representing the entity.
}
