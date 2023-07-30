import java.awt.Graphics;

public class Boundary {
    private Border topBorder,botBorder,leftBorder,rightBorder;
    Boundary() {
        this.topBorder = new Border(0, 0, Const.FRAME_WIDTH, Const.BORDER_WIDTH, "images/border.png");
        this.botBorder  = new Border(0, Const.FRAME_HEIGHT-Const.BORDER_WIDTH-20, Const.FRAME_WIDTH, Const.BORDER_WIDTH, "images/border.png");
        this.leftBorder  = new Border(0, 0, Const.BORDER_WIDTH, Const.FRAME_HEIGHT, "images/border.png");
        this.rightBorder  = new Border(Const.FRAME_WIDTH-Const.BORDER_WIDTH, 0, Const.BORDER_WIDTH, Const.FRAME_HEIGHT, "images/border.png");

    }
    public void drawBorders (Graphics g) {  // Draws all 4 borders
        topBorder.draw(g);
        botBorder.draw(g);
        leftBorder.draw(g);
        rightBorder.draw(g);        
    }
    // getters
    public Border getLeftBorder(){
        return this.leftBorder;
    }
    public Border getRightBorder(){
        return this.rightBorder;
    }
    public Border getTopBorder(){
        return this.topBorder;
    }
    public Border getBotBorder(){
        return this.botBorder;
    }
}
