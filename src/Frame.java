import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

public class Frame{ 
    private JFrame gameWindow;
    private GraphicsPanel canvas;
    private BasicKeyListener keyListener; 
    private boolean gameOver = false;
    private boolean winnerScreen = false;
    private boolean menuScreen = true;
    private Background gameOverScreen;
    private Background winnerBackground;
    private Background battleBackground = new Background("images/game_background.png");
    private Background menuBackground = new Background("images/menu.png");
    private Boundary boundary = new Boundary();
    private Player player = new Player();
    private Weapon weapon = new Weapon();
    private Wave wave = new Wave(0, 0, false, false);
//------------------------------------------------------------------------------  
    Frame() throws Exception{
        gameWindow = new JFrame("Demo Window");
        gameWindow.setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        
        canvas = new GraphicsPanel();
        gameWindow.add(canvas);
        
        keyListener = new BasicKeyListener();
        canvas.addKeyListener(keyListener); 
        
        gameWindow.setVisible(true);
    }
//------------------------------------------------------------------------------   
    public void run() throws Exception{
        while (!gameOver && !winnerScreen && !menuScreen) {
            gameWindow.repaint();
            try  {Thread.sleep(Const.FRAME_PERIOD);} catch(Exception e){}
            player.move();
            weapon.move();
            weapon.getMagazine().moveBullets();
            wave.getRound().moveEnemiesToward(player);
            wave.checkCollisions(player, weapon);
            if (!wave.getSpawned() && !wave.isFinalWave() && !wave.getCleared()){   // Checks if the round is cleared, if it is
                if (wave.getRound().getRoundNum() <= wave.getRoundNums() && wave.getRound().isCleared()){   // Spawn the next round
                    wave.spawnRound(wave.getRound().getRoundNum());
                    wave.getRound().setRoundNum(wave.getRound().getRoundNum()+1);
                }
            }
            if (wave.getRound().getFinalRoundFinished()){   // Checks if the wave is cleared
                wave.setWaveNum(wave.getWaveNum()+1);       // If the final round is cleared spawn next wave
                wave.setWaveSpawnList(wave.readWaveList(wave.getWaveNum()));
                wave.getRound().setRoundNum(0);
                wave.setSpawned(false);
                wave.setCleared(false);
            }
            if (player.isDead()){ // If the player is dead, end the game
                gameOver = true;
            }
            if (wave.isFinalWave()){ // Checks if the game is won
                winnerScreen = true;
            }
        }
        if (gameOver){  // If the game is over show the death screen
            reset();
            gameOverScreen = new Background("images/game_over_screen.png");
            gameWindow.repaint();
        }
        if (winnerScreen){  // If game is won show the winner screen
            reset();
            winnerBackground = new Background("images/winner_background.png");
            gameWindow.repaint();
        }
    }
    public void reset() throws Exception{   // Resets the game
        player.setHealth(Const.PLAYER_HEALTH);
        player.setX(Const.FRAME_WIDTH/2);
        player.setY(Const.FRAME_HEIGHT/2);
        weapon.setX(Const.FRAME_WIDTH/2+15);
        weapon.setY(Const.FRAME_HEIGHT/2+30);
        wave.setWaveSpawnList(wave.readWaveList(0));
        wave.setSpawned(false);
        wave.setCleared(false);
        wave.setWaveNum(0);
        wave.getRound().removeAll();
        wave.getRound().setRoundNum(0);
    }
//------------------------------------------------------------------------------  
    public class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        public void paintComponent(Graphics g){ 
            super.paintComponent(g); //required
            if (menuScreen){
                menuBackground.drawBackground(g);
            }
            if (winnerScreen){
                winnerBackground.drawBackground(g);
            }
            if (gameOver){
                gameOverScreen.drawBackground(g);
            }
            if (!gameOver && !winnerScreen && !menuScreen){
                battleBackground.drawBackground(g);
                boundary.drawBorders(g);
                player.draw(g);
                weapon.draw(g);
                weapon.getMagazine().drawBullets(g);
                wave.getRound().drawEnemies(g);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Sanserif",1,30));
                g.drawString("Wave "+(wave.getWaveNum()+1)+"/"+Const.FINAL_WAVE+" | Round "+(wave.getRound().getRoundNum()-1)+"/"+(wave.getRoundNums()-1), 40, 60);
            }
        }
    }
//------------------------------------------------------------------------------  
    public class BasicKeyListener implements KeyListener{      
        // method to process key pressed events (when a key goes down, i.e. immediately)
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (!player.collides(boundary.getLeftBorder()) && key == KeyEvent.VK_A){
                player.setSpeedX(-Const.PLAYER_SPEED);
                weapon.setSpeedX(-Const.PLAYER_SPEED);
            } else if (!player.collides(boundary.getRightBorder()) && key == KeyEvent.VK_D){
                player.setSpeedX(Const.PLAYER_SPEED);
                weapon.setSpeedX(Const.PLAYER_SPEED);
            } else if (!player.collides(boundary.getTopBorder()) && key == KeyEvent.VK_W){
                player.setSpeedY(-Const.PLAYER_SPEED);
                weapon.setSpeedY(-Const.PLAYER_SPEED);
            } else if (!player.collides(boundary.getBotBorder()) && key == KeyEvent.VK_S){
                player.setSpeedY(Const.PLAYER_SPEED);
                weapon.setSpeedY(Const.PLAYER_SPEED);
            } else {
                player.setSpeedX(0);
                player.setSpeedY(0);
                weapon.setSpeedX(0);
                weapon.setSpeedY(0);
            }
            if (key == KeyEvent.VK_LEFT){
                weapon.setAimDirection(1);
            } else if (key == KeyEvent.VK_RIGHT){
                weapon.setAimDirection(2);
            } else if (key == KeyEvent.VK_UP){
                weapon.setAimDirection(3);
            } else if (key == KeyEvent.VK_DOWN){
                weapon.setAimDirection(4);
            }
            if (key == KeyEvent.VK_ENTER && gameOver){
                gameOver = false;
            }
            if (key == KeyEvent.VK_ENTER && menuScreen){
                menuScreen = false;
            }
            if (key == KeyEvent.VK_SPACE && weapon.getAimDirection()!=0){
                weapon.fire();
            }
        }
        // method to process key released events (when a key goes up)
        public void keyReleased(KeyEvent e){     
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_D){
                player.setSpeedX(0);
                weapon.setSpeedX(0);
            } else if (key == KeyEvent.VK_W || key == KeyEvent.VK_S){
                player.setSpeedY(0);
                weapon.setSpeedY(0);
            }
            
        }       
        public void keyTyped(KeyEvent e){   
        }        
    }
}
