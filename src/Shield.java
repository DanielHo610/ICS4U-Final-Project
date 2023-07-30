import java.awt.Graphics;

public class Shield extends GameObject{
    private int health;
    public Shield(double x, double y){
        super(x, y, Const.SHIELD_WIDTH, Const.SHIELD_HEIGHT, "images/shield.png");
        this.health = Const.SHIELD_HEALTH;
    }
    public boolean shieldHit(Magazine magazine){ // Checks if the shield is hit
        for (int i=0; i<magazine.getBullets().size(); i++){
            if (this != null && magazine.getBullets().get(i) != null && magazine.getBullets().get(i).collides(this)){   // Sees if bullet collides with shield
                magazine.removeBullet(i);
                return true;
            }
        }
        return false;
    }
    public void reduceShieldHealth(){   // Reduces the shield health by the bullets damage
        this.setHealth(this.getHealth()-Const.BULLET_DMG);
    }
    public boolean isBroken(){  // Checks if the shield is broken
        if (this.getHealth() <= 0){ // If the shields health is below 0, shield is broken
            return true;
        }
        return false;
    }
    // Getters and Setters
    private void setHealth(int health){
        this.health = health;
    }
    private int getHealth() {
        return this.health;
    }
    @Override
    void draw(Graphics g) { // Draws the shield
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);            
    }
}