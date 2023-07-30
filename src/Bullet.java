import java.awt.Graphics;

public class Bullet extends GameObject{
    private int direction;
    public Bullet(double x, double y, int direction, String picName){
        super(x, y, Const.BULLET_WIDTH, Const.BULLET_HEIGHT, picName);
        this.direction = direction;
    }
    public void move(){ // Moves the bullets in certain direction
        if (this.direction == 1){ // Left
            this.setX(this.getX() - Const.BULLET_VEL);
        } else if (this.direction == 2){ // Right
            this.setX(this.getX() + Const.BULLET_VEL);
        } else if (this.direction == 3){ // Up
            this.setY(this.getY() - Const.BULLET_VEL);
        } else if (this.direction == 4){ // Down
            this.setY(this.getY() + Const.BULLET_VEL);
        }
    } 
    @Override
    public void draw(Graphics g){  // Draws bullet
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);    
    }   
}
