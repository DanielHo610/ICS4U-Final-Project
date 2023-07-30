import java.awt.Graphics;
import java.util.ArrayList;

public class Enemy extends GameObject{
    private int health;
    private double speed;
    private int damage;
    public Enemy(double x, double y, double width, double height, int health, double speed, int damage, String picName){
        super(x, y, width, height, picName);
        this.health = health;
        this.speed = speed;
        this.damage = damage;
    }
    public void moveToward(Player player){   // Moves the enemy toward the player
        double deltaX = 0;
        double deltaY = 0;
        deltaX = this.getX()-player.getX(); // Gets the x and y differences
        deltaY = this.getY()-player.getY();
        double vectorLength = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)); // Calculates the distance between the enemy and player
        if (vectorLength > 0){  
            deltaX = deltaX/vectorLength*this.speed; // Calculates how much the enemy should move based on their speed
            deltaY = deltaY/vectorLength*this.speed;
        }
        this.setX(this.getX()-deltaX);
        this.setY(this.getY()-deltaY);
    }
    public boolean isHit(Magazine magazine){    // Checks if an enemy is hot
        for (int i=0; i<magazine.getBullets().size(); i++){
            if (magazine.getBullets().get(i) != null && magazine.getBullets().get(i).collides(this)){   // If a bullet collides with the enemy
                magazine.removeBullet(i);                                                               // Return true
                return true;
            }
        }
        return false;
    }
    public boolean isKilled(ArrayList<Enemy> enemies){ // Checks if an enemy is killed
        for (int i = 0; i < enemies.size(); i++){
            if (enemies.get(i).getHealth() <= 0){   // Killed if enemy health is below 0
                return true;
            }
        }
        return false;
    }
    public void reduceHealth(){ // Reduces the health of an enemy
        this.setHealth(this.getHealth()-Const.BULLET_DMG);
    }
    // Getters and Setters
    public int getDamage(){
        return this.damage;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }

    @Override
    void draw(Graphics g) { // Draw an enemy
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
