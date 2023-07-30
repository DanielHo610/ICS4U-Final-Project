import java.awt.Graphics;

public class DamageEnemy extends Enemy{
    Explosive explosive;
    public DamageEnemy(double x, double y) {
        super(x, y, Const.DAMAGE_ENEMY_WIDTH, Const.DAMAGE_ENEMY_HEIGHT, Const.DAMAGE_ENEMY_HEALTH, Const.DAMAGE_ENEMY_SPEED, 
        Const.DAMAGE_ENEMY_DAMAGE, "images/damage_enemy.png");
        this.explosive = new Explosive(x, y);
    }
    public boolean isLow(){     // Checks if the enemys health is below 20
        if (this.getHealth() <= 20){
            return true;
        }
        return false;
    }
    public void throwExplosive(Player player){ // Throws the bomb that tracks the player
        this.explosive.move(player);
    }
    public void moveExplosive(){ // Moves the bomb with the enemy
        explosive.setX(this.getX());
        explosive.setY(this.getY());
    }
    public Explosive getExplosive(){
        return this.explosive;
    }
    public void drawExplosive(Graphics g){   // Draws the bomb
        this.explosive.draw(g);
    }
}
