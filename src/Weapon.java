import java.awt.Graphics;

public class Weapon extends GameObject{
    private int weaponVx;
    private int weaponVy;
    private int aimDirection;
    private Magazine magazine;
    public Weapon(){
        super(Const.FRAME_WIDTH/2+15, Const.FRAME_HEIGHT/2+30, Const.WEAPON_WIDTH, Const.WEAPON_HEIGHT, "images/weapon.png");
        this.magazine = new Magazine();
        this.weaponVx = 0;
        this.weaponVy = 0;
        this.aimDirection = 0;
    }
    public void fire(){ // Fires a bullet
        double bulletX = this.getX() + this.getW()/2;
        double bulletY = this.getY();
        magazine.getBullets().add(new Bullet(bulletX, bulletY, this.getAimDirection(), "images/bullet.png"));
    }
    public void move(){ // Moves the weapon with the player
        this.setX(this.getX() + this.weaponVx);
        this.setY(this.getY() + this.weaponVy);
    }
    // Gettesr and Setters
    public int getAimDirection(){
        return this.aimDirection;
    }
    public void setAimDirection(int aimDirection){
        this.aimDirection = aimDirection;
    }
    public Magazine getMagazine(){
        return this.magazine;
    }
    public void setSpeedX(int speedX){
        this.weaponVx = speedX;
    }
    public void setSpeedY(int speedY){
        this.weaponVy = speedY;
    }  
    @Override
    public void draw(Graphics g){   // Draws the weapon
        g.drawImage(getPicture(), (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}

