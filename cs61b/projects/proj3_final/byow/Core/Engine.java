package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

import static byow.TileEngine.Tileset.*;
import static java.awt.Color.*;
import static java.awt.Font.BOLD;

/**
 * source for the load file:
 * https://www.mkyong.com/java/how-to-read-file-in-java-fileinputstream/
 *
 * source for how to save file
 * https://alvinalexander.com/java/java-file-save-write-text-binary-data
 *
 */
public class Engine {

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */

    public static final int MENUWIDTH = 60;
    public static final int MENUHEIGHT = 60;
    public static final int WINDWIDTH = World.WINDWIDTH;
    public static final int WINDHEIGHT = World.WINDHEIGHT;


    private long seed;
    private String stringInput;

    protected boolean gameOver;
    protected Random random;

    /**
     * Gets a Random according to the seed that player sets.
     */
    private Random getRandom(long se) {
        return new Random(se);
    }

    public static void drawMenu() {
        // Draw the GUI
        Font title = new Font("Monaco", BOLD, 25);
        Font mainMenu = new Font("Monaco", Font.PLAIN, 16);
        StdDraw.setFont(title);
        StdDraw.setPenColor(white);
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT * 2.5 / 3, "==== PORTAL HOPPER 3 ====");
        StdDraw.setFont(mainMenu);
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT * 7.5 / 10, "New World (n / N)");
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT * 6.5 / 10, "Load World (l / L)");
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT * 5.5 / 10, "Quit World (q / Q)");
        StdDraw.show();
    }

    public void chooseAvatar() {
        StdDraw.clear(black);
        drawMessageFrame("Choose your avatar");
        StdDraw.pause(2000);
        StdDraw.clear(black);
        drawMessageFrame(" 'g' for gray | 'm' for magenta | 'b' for water");
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                input = Character.toLowerCase(input);
                switch(input) {
                    case('g'): {
                        AVATAR = new TETile('▲', gray, Color.black, "you");
                        break;
                    }case('m'): {
                        AVATAR = new TETile('❀', magenta, Color.black, "you");
                        break;
                    }case('b'): {
                        AVATAR = new TETile('≈', blue, Color.black, "you");
                        break;
                    } default: continue;
                }
                break;
            }
        }

    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void interactWithKeyboard() {
        drawBlank();
        drawMenu();
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 4, "Press your choice please: ");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        while (true) {
            String str = "";
            // if nothing is typed, do nothing
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            str += key;
            StdDraw.enableDoubleBuffering();
            StdDraw.clear(black);
            drawMenu();
            StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 4, "Press your choice please: " + str);
            StdDraw.show();
            //change switch statement below to an if or  else if statement
            // because you have to consider :Q
            //For the quitting part.

            switch (key) {
                case ('n'): {
                    String worldSeed = "";
                    String theString = "";
                    // null char value
                    char character = '\0';
                    StdDraw.clear(black);
                    drawMenu();
                    StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 4,
                            "Now please input a seed, then press 's' "
                                    + "to begin the game. " + "For example, 1234567s");
                    StdDraw.show();
                    while (character != 's') {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        character = StdDraw.nextKeyTyped();
                        if (Character.isDigit(character)) {
                            worldSeed += String.valueOf(character);
                        }
                        // the userInput string is the theString
                        theString += String.valueOf(character);
                        StdDraw.clear(black);
                        drawMenu();
                        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 4, "Your seed is: " + theString);
                        StdDraw.show();
                    }

                    seed = convertStringtoSeed(worldSeed);
                    chooseAvatar();
                    StdDraw.pause(1000);
                    //drawMessageFrame(String.valueOf(SEED));
                    ter.initialize(WINDWIDTH, WINDHEIGHT);
                    random = getRandom(seed);
                    World w = new World(random);
                    ter.renderFrame(w.getWorld());
                    playGame(w);
                    break;
                }
                case ('l'): {
                    ter.initialize(WINDWIDTH, WINDHEIGHT);
                    World w = loadWorld();
                    ter.renderFrame(w.getWorld());
                    gameOver = false;
                    playGame(w);
                    break;
                }
                case ('q'): {
                    gameOver = true;
                    System.exit(0);
                    break;
                }
                default:
            }
        }
    }

    private void drawBlank() {
        gameOver = false; //initialize the settings

        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(MENUWIDTH * 16, MENUHEIGHT * 16);
        Font font = new Font("Monaco", BOLD, 100);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, MENUWIDTH);
        StdDraw.setYscale(0, MENUHEIGHT);
        StdDraw.clear(black);
    }

    // Find a way to combine this method with
    // convertStringtoSeed to clean things up*/
    public long extractSeed(String input) {
        if (input.toLowerCase().contains("n") && input.toLowerCase().contains("s")) {
            long se;
            int start = input.toLowerCase().indexOf("n") + 1;
            int end = input.toLowerCase().indexOf("s");
            try {
                se = Long.parseLong(input.substring(start, end));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Seed has to be an integer but you input: \""
                        + input.substring(start, end) + "\"");
            }
            return se;
        } else {
            throw new RuntimeException("You must put a string start with 'n' and end with 's'.");
        }
    }
    /* abstract the number in Strings */
    public long convertStringtoSeed(String userInput) {
        String newString = "";
        // trim() get rid of spaces
        userInput = userInput.trim();
        if (!"".equals(userInput)) {
            for (int i = 0; i < userInput.length(); i++) {
                // 48-57 is the uniocde for 0-9
                if (userInput.charAt(i) > 47 && userInput.charAt(i) < 58) {
                    newString += userInput.charAt(i);
                }
            }
        }
        return Long.parseLong(newString);
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
//        ter.initialize(WINDWIDTH, WINDHEIGHT);
//
//        long se = extractSeed(input);
//        random = getRandom(se);
//        World world = new World(random);
//
//        ter.renderFrame(world.getWorld());
//
//        return world.getWorld();
// n7313251667695476404sasdw
        char letterMode = input.charAt(0);
        World world = startGame(letterMode, input);
        return world.world;
    }


    private boolean inBoundaries(double x, double y) {
        return x < WINDWIDTH && y < WINDHEIGHT;
    }
    // grabs the tile type by mouse
    public void getLocationMouse(World w) {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (inBoundaries(x, y)) {
            int xLimit = WINDWIDTH / 2;
            TETile[][] world = w.getWorld();
            ter.renderFrame(world);
            StdDraw.setPenColor(white);
            if (world[x][y].equals(LOCKED_DOOR)) {
                StdDraw.text(xLimit, 1, "A locked door "
                        + "find the key");
            } else if (world[x][y].equals(UNLOCKED_DOOR)) {
                StdDraw.text(xLimit, 1, "The door is unlocked! ");
            } else if (world[x][y].equals(KEY)) {
                StdDraw.text(xLimit, 1, "A key!");
            } else if (world[x][y].equals(PORTAL)) {
                StdDraw.text(xLimit, 1, "Where does this Portal go?");
            } else if (world[x][y].equals(WALL)) {
                StdDraw.text(xLimit, 1, "A wall! ");
            } else if (world[x][y].equals(AVATAR)) {
                StdDraw.text(xLimit, 1, "The player, YOU!");
            } else if (world[x][y].equals(FLOOR)) {
                StdDraw.text(WINDWIDTH / 2, 1, "A Floor!");
            } else {
                StdDraw.text(xLimit, 1, "This tile "
                        + "is a Nothing!");
            }
            StdDraw.show();
        }
    }
    // creates a message board for the user/ player
    public void drawMessageFrame(String message) {
        int width = MENUWIDTH / 2;
        int height = MENUHEIGHT / 2;
        StdDraw.clear();
        StdDraw.clear(black);
        // Draw the actual text
        Font bigFont = new Font("Monaco", BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(white);
        StdDraw.text(width, height, message);
        StdDraw.show();
    }
    public void drawMessageFrame(String message, int addHeight) {
        int width = MENUWIDTH / 2;
        int height = MENUHEIGHT / 2 - addHeight;
        StdDraw.clear();
        StdDraw.clear(black);
        // Draw the actual text
        Font bigFont = new Font("Monaco", BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(white);
        StdDraw.text(width, height, message);
        StdDraw.show();
    }


    // loads the game locally
    private static World loadWorld() {
        File file = new File("./World.txt");
        if (file.exists()) {
            try {
                FileInputStream fileStream = new FileInputStream(file);
                ObjectInputStream outputStream = new ObjectInputStream(fileStream);
                return (World) outputStream.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("no such file found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("no such class found");
                System.exit(0);
            }
        }
        /* In the case no World has been saved yet, we return a new one. */
        return new World(new Random(39209));
    }

    // saves the game locally
    private static void saveWorld(World nw) {
        File file = new File("./World.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
            outputStream.writeObject(nw);
        }  catch (FileNotFoundException e) {
            System.out.println("no such file found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    public World moveAlongWorld(World w, char direction) {
        Avatar av = w.getAvatar();
        //Position l = nw.getLockedDoor();// l for lockedDoor
        switch (direction) {
            case('a'): {
                av.moveLeft();
                break;
            } case('d'): {
                av.moveRight();
                break;
            } case('w'): {
                av.moveUp();
                break;
            } case('s'): {
                av.moveDown();
                break;
            }
            default:
        }
        return w;
    }
    // a is for avatar
    private void playGame(World w) {
        char key;
        // string holds the record of input or saved game info
        String str = "";
        while (!gameOver) {
            Avatar av = w.getAvatar();
            Door door = w.getDoor();
            getLocationMouse(w);
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                char k = Character.toLowerCase(key);
                str += k;
                if (w.gameOver) {
                    gameOver = true;
                    drawBlank();
                    drawMessageFrame("You win!");
                    StdDraw.pause(3500);
                    break;
                } else {
                }
                for (int i = 0; i < str.length() - 1; i++) {
                    if ((str.charAt(i) == ':' && str.charAt(i + 1) == 'q')) {
                        saveWorld(w);
                        drawBlank();
                        drawMessageFrame("Your game has been saved!");
                        StdDraw.pause(2000);
                        gameOver = true;
                    }
                }
                moveAlongWorld(w, key);
                ter.renderFrame(w.getWorld());

            }
        }
        drawBlank();
        drawMessageFrame("You Can Try Again!");
        StdDraw.pause(5000);
    }
    // used to parse out the seed from the direction moves
    // n7313251667695476404sasdw
    public int findStartInt(String input) {
        // the first index is taken out for 'n'
        int index = 1;
        while (Character.isDigit(input.charAt(index))) {
            index++;
        }

        return index;
    }
    // c1 is first and c2 is second char
    public boolean validEndString(char c1, char c2) {
        if (c1 == ':' && c2 == 'q') {
            return true;
        }
        if (c1 == ':' && c2 == 'Q') {
            return true;
        }
        return false;
    }
    private World startGame(char c, String input) {
        //pick the initial character to be playMode choice.
        switch (Character.toLowerCase(c)) {
            case ('n'): {
                seed = convertStringtoSeed(input);
                random = getRandom(seed);
                World w = new World(random);
                int startInt = findStartInt(input);
                for (int i = startInt; i < input.length(); i += 1) { // length - 1
                    if ((i != input.length() - 1)) { // length -1
                        w = moveAlongWorld(w, input.charAt(i));
                        if (validEndString(input.charAt(i), input.charAt(i + 1))) {
                            gameOver = true;
                            saveWorld(w);
                            break;
                        }
                    } else {
                        w = moveAlongWorld(w, input.charAt(i));
                        gameOver = true;
                        saveWorld(w);
                        break;
                    }
                }
                return w;
            }
            // load game
            case ('l'): {
                World w = loadWorld();
                int startInt = findStartInt(input);
                for (int i = startInt; i < input.length(); i += 1) {
                    if ((i != input.length() - 1)) {
                        w = moveAlongWorld(w, input.charAt(i));
                        if (validEndString(input.charAt(i), input.charAt(i + 1))) {
                            gameOver = true;
                            saveWorld(w);
                            break;
                        }
                    } else {
                        w = moveAlongWorld(w, input.charAt(i));
                        gameOver = true;
                        saveWorld(w);
                        break;
                    }
                }
                return w;
            }
            case ('q'): {
                gameOver = true;
                random = getRandom(seed);
                World w = new World(random);
                return w;
            } default: {
                gameOver = true;
                random = getRandom(seed);
                World w = new World(random);
                return w;
            }
        }
    }
}