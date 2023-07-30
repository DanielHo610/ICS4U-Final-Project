import java.awt.Graphics;

public class Player extends GameObject{
    private int health;
    private int playerVx;
    private int playerVy;

    public Player(){
        super(Const.FRAME_WIDTH/2, Const.FRAME_HEIGHT/2, Const.PLAYER_WIDTH, Const.PLAYER_HEIGHT, "images/player.png");
        this.health = Const.PLAYER_HEALTH;
        this.playerVx = 0;
        this.playerVy = 0;
    }
    public void move(){ // Moves the player
        this.setX(this.getX() + this.playerVx);
        this.setY(this.getY() + this.playerVy);
    }
    public void reducePlayerHealth(Enemy enemy){    // Reduces the player health by the enemys damage
        this.setHealth(this.getHealth()-enemy.getDamage());
    }
    public boolean isDead(){    // Checks if player is dead
        if(this.getHealth()<=0){ // If the players health is below 0, player is dead
            System.out.println("GAMEOVER");
            return true;
        }
        return false;
    }
    // Getters and Setters
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public void setSpeedX(int speedX){
        this.playerVx = speedX;
    }
    public void setSpeedY(int speedY){
        this.playerVy = speedY;
    }
    @Override
    public void draw(Graphics g){   // Draws the player
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
