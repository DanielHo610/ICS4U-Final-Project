import java.awt.Graphics;

public class LargeEnemy extends Enemy{
    private Shield shield;
    public LargeEnemy(double x, double y) {
        super(x, y, Const.LARGE_ENEMY_WIDTH, Const.LARGE_ENEMY_HEIGHT, Const.LARGE_ENEMY_HEALTH, Const.LARGE_ENEMY_SPEED,
        Const.LARGE_ENEMY_DAMAGE, "images/large_enemy.png");
        this.shield = new Shield(x, y);
    }
    public boolean shieldHit(Magazine magazine){   
        return this.shield.shieldHit(magazine);
    }
    public void reduceShieldHealth(){
        this.shield.reduceShieldHealth();
    }
    public boolean shieldIsBroken(){
        return shield.isBroken();
    }
    public void removeShield(){ // Removes the shield
        this.shield = null;
    }
    public void drawShield(Graphics g){ // Draws the shield
        shield.draw(g);
    }
    public void moveShield(){ // Moves the shield with the enemy
        shield.setX(this.getX()-10);
        shield.setY(this.getY()-10);
    }
    public Shield getShield(){
        return this.shield;
    }
}

