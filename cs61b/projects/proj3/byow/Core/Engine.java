package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Engine {
    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    protected static final int WINDWIDTH = 10000;
    protected static final int WINDHEIGHT = 10000;
    private static long SEED;
    private static final int MENUW = 40;
    private static final int MENUH = 60;
    private boolean gameOver;
    private int health;
    private int sandNumber;
    private String s;
    private int timeCounter;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void interactWithKeyboard() {
        showBlank();
        showMenu();
        StdDraw.text(MENUW / 2, MENUH / 4, "Press your choice please: ");
        StdDraw.show();
        health = 5;
        sandNumber = 0;
        timeCounter = 60;
        StdDraw.enableDoubleBuffering();
        while (true) {
            s = "";
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            s += key;
            StdDraw.enableDoubleBuffering();
            StdDraw.clear(Color.BLACK);
            showMenu();
            StdDraw.text(MENUW / 2, MENUH / 4, "Press your choice please: " + s);
            StdDraw.show();
            switch (key) {
                case ('n'):
                case ('N'): {
                    String sd = "";
                    String trueSeed = "";
                    char c = 'l';
                    StdDraw.clear(Color.BLACK);
                    showMenu();
                    StdDraw.text(MENUW / 2, MENUH / 4,
                            "Now please input a seed, then press 's' to start the game.");
                    StdDraw.show();
                    do {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        c = StdDraw.nextKeyTyped();
                        if (c >= 48 && c <= 57) {
                            trueSeed += String.valueOf(c);
                        }
                        sd += String.valueOf(c);
                        if (c != 's') {
                            StdDraw.clear(Color.BLACK);
                            showMenu();
                            StdDraw.text(MENUW / 2, MENUH / 4, "Your seed is: " + sd);
                            StdDraw.show();
                        }
                    } while (c != 's');

                    SEED = getStringtoNum(trueSeed);
                    StdDraw.pause(500);
                    System.out.println("## Game final SEED: " + SEED);

                    InitialWorld wgp = new InitialWorld(80, 30, SEED);
                    ter.initialize(wgp.getWidth(), wgp.getHeight());
                    NewWorld nw = CreateWorld.generate(wgp);
                    ter.renderFrame(nw.getWorld());
                    playGame(nw);
                    break;
                }
                case ('l'):
                case ('L'): {
                    NewWorld nw = loadCrazyWorld();
                    ter.initialize(80, 30);
                    ter.renderFrame(nw.getWorld());
                    gameOver = false;
                    playGame(nw);
                    break;
                }
                case ('q'):
                case ('Q'): {
                    gameOver = true;
                    System.exit(0);
                    break;
                }
                default:
            }
        }
    }

    private void playGame(NewWorld nw) {

//        new Thread(() -> {
//            while (timeCounter > 0) {
//                StdDraw.enableDoubleBuffering();
//
//                timeCounter--;
//                //long hh = timeCounter / 60 / 60 % 60;
//                //long mm = timeCounter / 60 % 60;
//                //long ss = timeCounter % 60;
//                //System.out.println("left + hh + "hours" + mm + "minutes" + ss + "seconds");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        char key;
        String record = "";
        while (!gameOver) {
            mousePointer(nw);
            //System.out.println(StdDraw.hasNextKeyTyped());
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            record += key;
            if (health == 0 || timeCounter == 0) {
                gameOver = true;
                health = 5;
                sandNumber = 0;
                //System.out.println("You lose!");
                showBlank();
                drawFrame("Sorry! You lose!");
                StdDraw.pause(5000);
                break;
            } else if ((nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY() - 1].equals(Tileset
                    .LOCKED_DOOR)) && health >= 5 && sandNumber >= 2 && timeCounter > 0) {
                gameOver = true;
                health = 5;
                sandNumber = 5;
                showBlank();
                drawFrame("Congratulation! You win!");
                StdDraw.pause(5500);
                break;
            }
            //System.out.println(record);
            for (int i = 0; i < record.length() - 1; i += 1) {
                if ((record.charAt(i) == ':' && record.charAt(i + 1) == 'q')
                        || (record.charAt(i) == ':' && record.charAt(i + 1) == 'Q')) {
                    saveCrazyWorld(nw);
                    showBlank();
                    drawFrame("Your game has been saved!");
                    StdDraw.pause(3000);
                    gameOver = true;
                }
            }
            nw = move(nw, key);
        }
        showBlank();
        drawFrame("Have an another try next time!");
        StdDraw.pause(5000);
    }

    private void mousePointer(NewWorld nw) {
        int mx = (int) StdDraw.mouseX();
        int my = (int) StdDraw.mouseY();
        if (nw.getWorld()[mx][my].equals(Tileset.LOCKED_DOOR)) {
            ter.renderFrame(nw.getWorld());
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "This is a door "
                    + "where you can escape from this crazy world!");
        } else if (nw.getWorld()[mx][my].equals(Tileset.WALL)) {
            ter.renderFrame(nw.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "This is a wall where you can't go, "
                    + "otherwise you'll lose one life!");
        } else  if (nw.getWorld()[mx][my].equals(Tileset.AVATAR)) {
            ter.renderFrame(nw.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "You, the player!");
        } else if (nw.getWorld()[mx][my].equals(Tileset.FLOOR)) {
            ter.renderFrame(nw.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "Floor!");
        } else if (nw.getWorld()[mx][my].equals(Tileset.FLOWER)) {
            ter.renderFrame(nw.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "Flower! You can eat it to add health value!");
        } else if (nw.getWorld()[mx][my].equals(Tileset.SAND)) {
            ter.renderFrame(nw.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "Sand! You need to clean it to win the game!");
        } else {
            ter.renderFrame(nw.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WINDWIDTH / 2, 1, "Nothing!");
        }
        StdDraw.text(WINDWIDTH  * 4 / 5, WINDHEIGHT - 1,
                "Your Health Value: " + Integer.toString(health));
        StdDraw.text(WINDWIDTH / 2, WINDHEIGHT - 1,
                "Time Left: " + Integer.toString(timeCounter));
        StdDraw.text(WINDWIDTH  * 1 / 5, WINDHEIGHT  - 1,
                "Sand You have cleaned: " + Integer.toString(sandNumber));
        //StdDraw.text(WINDWIDTH  * 1 / 5, WINDHEIGHT * 3 / 5, s);
        StdDraw.show();
    }

    public void drawFrame(String str) {
        int midWidth = MENUW / 2;
        int midHeight = MENUH / 2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        // Draw the actual text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, str);
        StdDraw.show();
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
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char playMode = input.charAt(0);
        System.out.println("MODE：" + playMode);
        return enterGame(playMode, input);
    }

    private NewWorld move(NewWorld nw, char key) {
        TETile upper = nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY() + 1];
        TETile lower = nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY() - 1];
        TETile right = nw.getWorld()[nw.getAvatar().getX() + 1][nw.getAvatar().getY()];
        TETile left = nw.getWorld()[nw.getAvatar().getX() - 1][nw.getAvatar().getY()];
        switch (key) {
            case ('w'):
            case ('W'): {
                System.out.println("W");
                if (upper.equals(Tileset.WALL)) {
                    health -= 1;
                    return nw;
                } else {
                    if (upper.equals(Tileset.FLOWER)) {
                        health += 1;
                    } else if (upper.equals(Tileset.SAND)) {
                        sandNumber += 1;
                    }
                    nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY() + 1] = Tileset.AVATAR;
                    nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY()] = Tileset.FLOOR;
                    Position newPlayer = new Position(nw.getAvatar().getX(), nw.getAvatar().getY() + 1);
                    return new NewWorld(nw.getWorld(), nw.getLockedDoor(), newPlayer);
                }
            }
            case ('s'):
            case ('S'): {
                System.out.println("S");
                if (lower.equals(Tileset.WALL)) {
                    health -= 1;
                    return nw;
                } else if (lower.equals(Tileset.LOCKED_DOOR)) {
                    gameOver = true;
                    return nw;
                } else {
                    if (lower.equals(Tileset.FLOWER)) {
                        health += 1;
                    } else if (lower.equals(Tileset.SAND)) {
                        sandNumber += 1;
                    }
                    nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY() - 1] = Tileset.AVATAR;
                    nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY()] = Tileset.FLOOR;
                    Position newPlayer = new Position(nw.getAvatar().getX(), nw.getAvatar().getY() - 1);
                    return new NewWorld(nw.getWorld(), nw.getLockedDoor(), newPlayer);
                }
            }
            case ('a'):
            case ('A'): {
                System.out.println("A");
                if (left.equals(Tileset.WALL)) {
                    health -= 1;
                    return nw;
                } else {
                    if (left.equals(Tileset.FLOWER)) {
                        health += 1;
                    } else if (left.equals(Tileset.SAND)) {
                        sandNumber += 1;
                    }
                    nw.getWorld()[nw.getAvatar().getX() - 1][nw.getAvatar().getY()] = Tileset.AVATAR;
                    nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY()] = Tileset.FLOOR;
                    Position newPlayer = new Position(nw.getAvatar().getX() - 1, nw.getAvatar().getY());
                    return new NewWorld(nw.getWorld(), nw.getLockedDoor(), newPlayer);
                }
            }
            case ('d'):
            case ('D'): {
                System.out.println("D");
                if (right.equals(Tileset.WALL)) {
                    health -= 1;
                    return nw;
                } else {
                    if (right.equals(Tileset.FLOWER)) {
                        health += 1;
                    } else if (right.equals(Tileset.SAND)) {
                        sandNumber += 1;
                    }
                    nw.getWorld()[nw.getAvatar().getX() + 1][nw.getAvatar().getY()] = Tileset.AVATAR;
                    nw.getWorld()[nw.getAvatar().getX()][nw.getAvatar().getY()] = Tileset.FLOOR;
                    Position newPlayer = new Position(nw.getAvatar().getX() + 1, nw.getAvatar().getY());
                    return new NewWorld(nw.getWorld(), nw.getLockedDoor(), newPlayer);
                }
            } default: return nw;
        }
    }

    private TETile[][] enterGame(char mode, String input) {
        //pick the initial character to be playMode choice.
        switch (mode) {
            case ('n'):
            case ('N'): {
                SEED = getStringtoNum(input);
                InitialWorld wgp = new InitialWorld(WINDWIDTH, WINDHEIGHT, SEED);
                //ter.initialize(wgp.getWidth(), wgp.getHeight());
                NewWorld nw = CreateWorld.generate(wgp);
                //ter.renderFrame(nw.getWorld());

                int start = 1;
                for (int i = 0; i < input.length(); i += 1) {
                    if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                        start = i + 1;
                        break;
                    }
                }
                for (int i = start; i < input.length(); i += 1) {
                    nw = move(nw, input.charAt(i));
                    if ((input.charAt(i) == ':' && input.charAt(i + 1) == 'q')
                            || (input.charAt(i) == ':' && input.charAt(i + 1) == 'Q')) {
                        gameOver = true;
                        saveCrazyWorld(nw);
                        System.out.println("Saved");
                        break;
                    }
                }
                return nw.getWorld();
            }
            case ('l'):
            case ('L'): {
                //load game.
                NewWorld nw = loadCrazyWorld();
                int start = 1;
                for (int i = 0; i < input.length(); i += 1) {
                    if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                        start = i + 1;
                        break;
                    }
                }
                for (int i = start; i < input.length(); i += 1) {
                    if ((input.charAt(i) == ':' && input.charAt(i + 1) == 'q')
                            || (input.charAt(i) == ':' && input.charAt(i + 1) == 'Q')) {
                        gameOver = true;
                        saveCrazyWorld(nw);
                        System.out.println("Saved");
                        break;
                    }
                    nw = move(nw, input.charAt(i));
                }
                return nw.getWorld();
            }
            case ('q'):
            case ('Q'): {
                gameOver = true;
                TETile[][] world = new TETile[80][30];
                for (TETile[] x : world) {
                    for (TETile y : x) {
                        y = Tileset.NOTHING;
                    }
                }
                return world;
            } default: {
                gameOver = true;
                TETile[][] world = new TETile[80][30];
                for (TETile[] x : world) {
                    for (TETile y : x) {
                        y = Tileset.NOTHING;
                    }
                }
                return world;
            }
        }
    }

    private void showBlank() {
        gameOver = false; //initialize the settings

        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(MENUW * 16, MENUH * 16);
        Font font = new Font("Monaco", Font.BOLD, 100);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, MENUW);
        StdDraw.setYscale(0, MENUH);
        StdDraw.clear(Color.BLACK);
    }

    private void showMenu() {
        // Draw the GUI
        Font title = new Font("Monaco", Font.BOLD, 25);
        Font mainMenu = new Font("Monaco", Font.PLAIN, 16);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MENUW / 2, MENUH * 2 / 3, "==== CS61B proj2: Cool Game! ====");
        StdDraw.setFont(mainMenu);
        StdDraw.text(MENUW / 2, MENUH * 5.5 / 10, "New Game (n / N)");
        StdDraw.text(MENUW / 2, MENUH * 4.5 / 10, "Load Game (l / L)");
        StdDraw.text(MENUW / 2, MENUH * 3.5 / 10, "Quit (q / Q)");
        //StdDraw.show();
    }

    /* abstract the number in Strings */
    private long getStringtoNum(String str) {
        str = str.trim();
        String str2 = "";
        if (!"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 = str2 + str.charAt(i);
                }
            }
        }
        return Long.parseLong(str2);
    }

    private static NewWorld loadCrazyWorld() {
        File f = new File("./crazyWorld.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (NewWorld) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        /* In the case no World has been saved yet, we return a new one. */
        return CreateWorld.generate(new InitialWorld(80, 30, 567));
    }

    private static void saveCrazyWorld(NewWorld nw) {
        File f = new File("./crazyWorld.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(nw);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}