import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays; // Import for Arrays.fill method

public class TreasureHunt {
    private static final int MAP_WIDTH = 21;
    private static final int MAP_HEIGHT = 21;
    private int life = 3;
    private static final char EMPTY = '.';
    private static final char PLAYER_TRAIL = '~';
    private boolean firstMoveMade = false;


    private final char[][] map = new char[MAP_HEIGHT][MAP_WIDTH];
    private Player player;
    private List<GameEntity> entities;

    public TreasureHunt() {
        entities = new ArrayList<>();
        setupGame();
        updateMap();
        firstMoveMade = false;
    }

    private void setupGame() {
        // Initialize the player and add to entities
        player = new Player(0, 20); // or any starting position of your choice
        entities.add(player);

        // Add walls, traps, and other entities. For now, we add them manually.
        entities.add(new Wall(5, 3));
         entities.add(new Wall(18, 2));
        
        // within the TreasureHunt constructor after placing the player and treasure...

        // Manually adding some walls
        for (int i = 0; i < MAP_HEIGHT - 5; i++) {
            entities.add(new Wall(5, i));
            }
        for (int i = 0; i < MAP_HEIGHT - 5; i++) {
        entities.add(new Wall(i, 6));
        }
        for (int i = 12; i < MAP_HEIGHT - 5; i++) {
            entities.add(new Wall(i, 16));
            }
        for (int i = 3; i < MAP_WIDTH - 7; i++) {
            entities.add(new Wall(8, i));
            }
        for (int i = 5; i < MAP_WIDTH - 8; i++) {
            entities.add(new Wall(12, i));
            }
        for (int i = 0; i < MAP_WIDTH - 12; i++) {
            entities.add(new Wall(17, i));
            }
        for (int i = 0; i < MAP_WIDTH - 13; i++) {
            entities.add(new Wall(19, i));
            }
        for (int i = 0; i < MAP_WIDTH - 2; i++) {
            entities.add(new Wall(13, i));
            }
        for (int i = 0; i < MAP_WIDTH - 12; i++) {
            entities.add(new Wall(i, 19));
            }
        for (int i = 0; i < MAP_WIDTH - 10; i++) {
            entities.add(new Wall(i, 16));
            }
        for (int i = 18; i < MAP_WIDTH - 1; i++) {
            entities.add(new Wall(i, 16));
            }
        for (int i = 15; i < MAP_WIDTH - 2; i++) {
            entities.add(new Wall(i, 20));
            }
        for (int i = 15; i < MAP_WIDTH - 2; i++) {
            entities.add(new Wall(18, i));
            }
        for (int i = 15; i < MAP_WIDTH ; i++) {
            entities.add(new Wall(i, 13));
            }
        
            entities.add(new Trap(10, 7));
            entities.add(new Trap(6, 10));
            entities.add(new Trap(11, 20));
            entities.add(new Trap(12, 18));
            entities.add(new Trap(13, 19));
            entities.add(new Trap(19, 8));
            entities.add(new Trap(19, 10));
            entities.add(new Trap(7, 9));
            entities.add(new Trap(17, 9));
            entities.add(new Trap(20, 10));
    

        // Initialize the treasure's location (you can randomize this)
        entities.add(new GameEntity(20, 0) {
            @Override
            public char getSymbol() {
                return '*'; // Symbol for the treasure
            }
        });
    }

    private void updateMap() {
        // Clear the map but preserve the player's trail.
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (!firstMoveMade || (firstMoveMade && map[y][x] != PLAYER_TRAIL)) {
                    map[y][x] = EMPTY;
                }
                if (map[y][x] != PLAYER_TRAIL) {
                    map[y][x] = EMPTY;}
            }
        }

        // Set entities on the map
        for (GameEntity entity : entities) {
            if (entity instanceof Player) {
                // Place the player's trail.
                map[entity.getY()][entity.getX()] = PLAYER_TRAIL;
            } else {
                // Place other entities.
                map[entity.getY()][entity.getX()] = entity.getSymbol();
            }
        }

        // Finally, place the player on the map.
        map[player.getY()][player.getX()] = player.getSymbol();
    }

    private void printMap() {
    int visibilityRange = 3;

    for (int i = 0; i < MAP_HEIGHT; i++) {
        for (int j = 0; j < MAP_WIDTH; j++) {
            if (!firstMoveMade || 
                (i >= player.getY() - visibilityRange && 
                 i <= player.getY() + visibilityRange && 
                 j >= player.getX() - visibilityRange && 
                 j <= player.getX() + visibilityRange)) {
                System.out.print(map[i][j] + " ");
            } else {
                System.out.print("? ");
            }
        }
        System.out.println();
    }
}

    
    

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameRunning = true;
    
        printMap(); // Initial map print
    
        while (gameRunning) {
            String input = scanner.nextLine().toLowerCase();
            int deltaX = 0, deltaY = 0;
    
            switch (input) {
                case "w":
                    deltaY = -1; // move up
                    break;
                case "a":
                    deltaX = -1; // move left
                    break;
                case "s":
                    deltaY = 1; // move down
                    break;
                case "d":
                    deltaX = 1; // move right
                    break;
                default:
                    System.out.println("Invalid input. Use W, A, S, or D to move.");
                    continue;
            }
    
            // Mark the current position with the player's trail before moving
            map[player.getY()][player.getX()] = PLAYER_TRAIL;
    
            // Predict the player's new position
            int newX = player.getX() + deltaX;
            int newY = player.getY() + deltaY;
            if (!firstMoveMade) {
                firstMoveMade = true; // The player has made the first move, so set the flag to true
            }
    
            if (newX >= 0 && newX < MAP_WIDTH && newY >= 0 && newY < MAP_HEIGHT) {
                char nextPositionContent = map[newY][newX];
    
                switch (nextPositionContent) {
                    case '#': // Wall
                        System.out.println("You hit a wall!");
                        break;
                    case 'X': // Trap
                        System.out.println("You hit a trap!");
                        life--;
                        System.out.println("Lives remaining: " + life);
                        if (life == 0) {
                            System.out.println("Game over!");
                            gameRunning = false;
                        }
                        break;
                    case '*': // Treasure
                        System.out.println("You've found the treasure!");
                        gameRunning = false; // Exit the game loop
                        break;
                    default: // Empty space
                        player.move(deltaX, deltaY); // Move the player
                        updateMap();
                        printMap();
                }
            } else {
                System.out.println("You can't move outside the map!");
            }
        }
    
        scanner.close(); // It's important to close the scanner
    

            // Check if the player reached the treasure
            if (map[player.getY()][player.getX()] == '*') {
            System.out.println("You've found the treasure!");
            System.out.println("Well done you won");
            gameRunning = false; // Exit the game loop
            }     
              scanner.close();
        }
      

      
    
    public static void main(String[] args) {
        TreasureHunt game = new TreasureHunt();
        game.startGame();
    }
}

