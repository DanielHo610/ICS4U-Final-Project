import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage background;
    public Background(String backgroundName){
        try {                
            this.background = ImageIO.read(new File(backgroundName)); // Reads the file of the background image
        }catch (IOException ex){}  
    }
    public void drawBackground(Graphics g){ // Draws background
        g.drawImage(this.background, 0, 0, Const.FRAME_WIDTH, Const.FRAME_HEIGHT, null);
    }
}
