import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

abstract class GameObject{
    private double x;
    private double y;
    private double width;
    private double height;
    private BufferedImage picture;    
//------------------------------------------------------------------------------    
    GameObject(double x, double y, double width, double height, String picName){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        try {                
            this.picture = ImageIO.read(new File(picName));
        }catch (IOException ex){}   
    }
//------------------------------------------------------------------------------
    public boolean collides(GameObject other) { // Checks if a game object collides with another
        return getRight() >= other.getLeft()
                && getLeft() <= other.getRight()
                && getTop() <= other.getBottom()
                && getBottom() >= other.getTop();
    }
    // Getters and Setters
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getW(){
        return this.width;
    }    
    public double getH(){
        return this.height;
    }    
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public BufferedImage getPicture(){
        return this.picture;
    }
    public double getLeft() {
        return x;
    }
    public double getRight() {
        return x + width;
    }
    public double getTop() {
        return y;
    }
    public double getBottom() {
        return y + height;
    }
//------------------------------------------------------------------------------    
    abstract void draw(Graphics g);
}
