import java.awt.Graphics;

public class Border extends GameObject{
    Border(int x, int y, int width, int height, String picName){
        super(x, y, width, height, picName);
    }
    @Override
    void draw(Graphics g) { // Draw border
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
    
}