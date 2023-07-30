import java.awt.Graphics;

public class Explosive extends GameObject{
    public Explosive(double x, double y){
        super(x, y, Const.EXPLOSIVE_WIDTH, Const.EXPLOSIVE_HEIGHT, "images/explosive.png");
    }
    public void move(Player player){   // Moves the explosive toward the player
        double deltaX = 0;
        double deltaY = 0;
        deltaX = this.getX()-player.getX(); // Gets the x and y differences
        deltaY = this.getY()-player.getY();
        double vectorLength = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)); // Calculates the distance between the explosive and player
        if (vectorLength > 0){  
            deltaX = deltaX/vectorLength*Const.EXPLOSIVE_SPEED; // Calculates how much the explosive should move based on the explosive speed
            deltaY = deltaY/vectorLength*Const.EXPLOSIVE_SPEED;
        }
        this.setX(this.getX()-deltaX);
        this.setY(this.getY()-deltaY);
    }
    @Override
    void draw(Graphics g) {
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);            
    }
}
