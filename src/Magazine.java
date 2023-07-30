import java.awt.Graphics;
import java.util.ArrayList;

public class Magazine{
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();   
    
    Magazine(){
        this.bullets = new ArrayList<Bullet>();
    }
//------------------------------------------------------------------------------      
    public ArrayList<Bullet> getBullets(){
        return this.bullets;
    }
    public void drawBullets(Graphics g){    // Draws all bullets shot
        for (int i=0; i<this.bullets.size(); i++){
            if (this.bullets.get(i) != null){
                this.bullets.get(i).draw(g);
            }
        }
    }    
    public void moveBullets(){  // Moves all bullets shot
        for (int i=0; i<this.bullets.size(); i++){
            if (this.bullets.get(i) != null){
                this.bullets.get(i).move();
                if (this.bullets.get(i).getY() < 0 || this.bullets.get(i).getX() < 0 || 
                    this.bullets.get(i).getY() > Const.FRAME_HEIGHT || this.bullets.get(i).getX() > Const.FRAME_WIDTH){
                    this.removeBullet(i);
                }
            }
        }
    }
    public void removeBullet(int index){    // Removes a bullet from the magazine
        this.bullets.remove(index);
    }    
}
