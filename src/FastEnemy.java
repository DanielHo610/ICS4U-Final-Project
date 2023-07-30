public class FastEnemy extends Enemy {
    public FastEnemy(double x, double y) {
        super(x, y, Const.FAST_ENEMY_WIDTH, Const.FAST_ENEMY_HEIGHT, Const.FAST_ENEMY_HEALTH, Const.FAST_ENEMY_SPEED, 
        Const.FAST_ENEMY_DAMAGE, "images/fast_enemy.png");
    }
    public boolean isLow(){ // Checks if the enemy is below 10 hp
        if (this.getHealth() <= 10){
            return true;
        }
        return false;
    }
    
}
