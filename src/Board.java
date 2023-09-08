import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener, KeyListener {

    private final int SPRITE_SIZE = 50;
    private final int MOVE_SPEED = 5;
    private ArrayList<String[]> levelData;
    private ArrayList<Sprite> spriteObjects;
    private Timer timer;
    private Sprite playerCharacter, ladder;
    private int currentLevel = 1;

    public Board() {
        setBackground(Color.GREEN);
        addKeyListener(this);
        setFocusable(true);
        loadLevel(currentLevel);
        loadValues();
    }
    // loads values from csv file based on current level
    private void loadLevel(int level) {
        levelData = new ArrayList<String[]>();
        spriteObjects = new ArrayList<>();
        try  {
            File file = new File("levels/level" + level + ".csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                levelData.add(tempArr);
            }
            br.close();
        } catch (IOException ioe) {
            System.out.println("IOE: " + ioe.getLocalizedMessage());
        }

        for (int row = 0; row < levelData.size(); row++) {
            String[] tempArray = levelData.get(row);
            for (int col = 0; col < tempArray.length; col++) {
                if (tempArray[col].equals("0")) {
                    Sprite s = new Sprite(new ImageIcon("images/terrain/dirt1.png").getImage());
                    s.setX(SPRITE_SIZE * col);
                    s.setY(SPRITE_SIZE * row);
                    s.setSolid(false);
                    spriteObjects.add(s);
                } else if (tempArray[col].equals("1")) {
                    Sprite s = new Sprite(new ImageIcon("images/terrain/block1.png").getImage());
                    s.setX(SPRITE_SIZE * col);
                    s.setY(SPRITE_SIZE * row);
                    s.setSolid(true);
                    spriteObjects.add(s);
                } else if (tempArray[col].equals("2")) {
                    Sprite s = new Sprite(new ImageIcon("images/terrain/ladder.png").getImage());
                    s.setX(SPRITE_SIZE * col);
                    s.setY(SPRITE_SIZE * row);
                    s.setSolid(false);
                    spriteObjects.add(s);
                    ladder = new Sprite(new ImageIcon("images/terrain/ladder.png").getImage(), SPRITE_SIZE * col, SPRITE_SIZE * row);
                } else if (tempArray[col].equals("3")) {
                    Sprite s = new Sprite(new ImageIcon("images/terrain/lava.png").getImage());
                    s.setX(SPRITE_SIZE * col);
                    s.setY(SPRITE_SIZE * row);
                    s.setSolid(false);
                    spriteObjects.add(s);
                }
            }
        }
    }

    private void loadValues() {
        playerCharacter = new Sprite(new ImageIcon("images/characters/red-slime.gif").getImage(), 100, 100);
        timer = new Timer(50, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawWorld(g2d);
        drawCharacters(g2d);
    }

    private void drawWorld(Graphics2D g2d) {
        for (Sprite sprite : spriteObjects)
            g2d.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    }

    private void drawCharacters(Graphics2D g2d) {
        g2d.drawImage(playerCharacter.getImage(), playerCharacter.getX(), playerCharacter.getY(), this);
    }
    // simple collision detected that is called whenever the player tries to move
    private boolean isCollision(int x, int y) {
        for (Sprite sprite : spriteObjects) {
            if (sprite.isSolid()) {
                Rectangle player = new Rectangle(playerCharacter.getX() + x, playerCharacter.getY() + y, 50, 50);
                Rectangle wall = new Rectangle(sprite.getX(), sprite.getY(), 50, 50);
                if (player.intersects(wall))
                    return true;
            }
        }
        return false;
    }
    // checks if the player interacts with the level's ladder
    private void checkForInteraction() {
        Rectangle player = new Rectangle(playerCharacter.getX(), playerCharacter.getY(), 40, 40);
        Rectangle tempLadder = new Rectangle(ladder.getX(), ladder.getY(), 50, 50);
        if (player.intersects(tempLadder)) {
            System.out.println("Ladder activated");
            currentLevel += 1;
            ladder = null;
            loadLevel(currentLevel);
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyNumber = Character.getNumericValue(keyChar);
        if (keyChar == 'd') {
            if (!isCollision(MOVE_SPEED, 0))
                playerCharacter.changeX(MOVE_SPEED);
        } else if (keyChar == 'a') {
            if (!isCollision(-MOVE_SPEED, 0))
                playerCharacter.changeX(-5);
        } else if (keyChar == 'w') {
            if (!isCollision(0, -MOVE_SPEED))
                playerCharacter.changeY(-5);
        } else if (keyChar == 's') {
            if (!isCollision(0, MOVE_SPEED))
                playerCharacter.changeY(5);
        } else if (keyChar == 'e') {
            checkForInteraction();
        }
    }

    public void keyReleased(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyNumber = Character.getNumericValue(keyChar);
        if (keyChar == 'd') {
            playerCharacter.changeX(0);
        } else if (keyChar == 'a') {
            playerCharacter.changeX(0);

        } else if (keyChar == 'w') {
            playerCharacter.changeY(0);
        } else if (keyChar == 's') {
            playerCharacter.changeY(0);
        }
    }

}
