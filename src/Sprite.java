import java.awt.*;

public class Sprite {

    private Image image;
    private int x, y;
    private boolean isSolid;

    public Sprite(Image image) {
        this.image = image;
    }

    public Sprite(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTop() {
        return this.y;
    }

    public int getBottom() {
        return this.y + 50;
    }

    public int getRight() {
        return this.x + 50;
    }

    public int getLeft() {
        return this.x;
    }

    public void changeX(int x) {
        this.x += x;
    }

    public void changeY(int y) {
        this.y += y;
    }

    public void setSolid(boolean solid) {
        this.isSolid = solid;
    }

    public boolean isSolid() {
        return this.isSolid;
    }
}
