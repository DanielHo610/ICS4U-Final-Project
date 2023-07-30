import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Graphics;

public class Round {
    private Map<Integer,Integer> horSpawnCoordinates;
    private Map<Integer,Integer> verSpawnCoordinates;
    private ArrayList<Enemy> enemies;
    private int roundNum = 0;
    private boolean spawned;
    private boolean finalRound;
    Round(int roundNum, boolean spawned) {
        this.enemies = new ArrayList<Enemy>();
        this.horSpawnCoordinates = genHorSpawnCoordinates();
        this.verSpawnCoordinates = genVerSpawnCoordinates();
        this.roundNum = roundNum;
        this.spawned = spawned;
        this.finalRound = false;
    }
    public void spawn(ArrayList<String> waveSpawnList){ // Spawns the enemies from the text file
        for (int i = 0; i < waveSpawnList.size(); i++){
            String enemyType = waveSpawnList.get(i).substring(0, waveSpawnList.get(i).length()-2); // Gets the enemy type being spawned
            int roundNumInFile = Character.getNumericValue(waveSpawnList.get(i).charAt(waveSpawnList.get(i).length()-1)); // Gets the round number
            if (roundNumInFile == this.roundNum && enemyType.equals("largeenemy")){ // Spawns a large enemy
                this.enemies.add(this.generateEnemy(enemyType));
            } else if (roundNumInFile == this.roundNum && enemyType.equals("fastenemy")){ // Spawns a fast enemy
                this.enemies.add(this.generateEnemy(enemyType));
            } else if (roundNumInFile == this.roundNum && enemyType.equals("damageenemy")){ // Spawns a damage enemy
                this.enemies.add(this.generateEnemy(enemyType));
            }
            if (roundNumInFile == this.roundNum && enemyType.equals("waveclear")){ // Checks if the wave is cleared
                this.setfinalRoundFinished(true);
            }
        }
    }
    public Enemy generateEnemy(String enemyType){   // Generates enemy in random location along the border
        int borderPlace = (int)((2)*Math.random()+1);
        int xLoc = 0;
        int yLoc = 0;
        if (enemyType.equals("largeenemy")){ // Random large enemy
            if (borderPlace == 1){  // Spawns on horizontal border
                xLoc = this.randXLoc();                     // Gets a random x location
                yLoc = horSpawnCoordinates.get(xLoc);       // Gets the coressponding y location to the x location
            } else if (borderPlace == 2){ // Spawns on vertical border
                yLoc = this.randYLoc();                     // Gets a random y locations
                xLoc = verSpawnCoordinates.get(yLoc);       // Gets the coressponding x location to the y location
            }
            LargeEnemy largeEnemy = new LargeEnemy(xLoc, yLoc);
            return largeEnemy;
        } else if (enemyType.equals("fastenemy")){ // Random fast enemy
            if (borderPlace == 1){
                xLoc = this.randXLoc();
                yLoc = horSpawnCoordinates.get(xLoc);
            } else if (borderPlace == 2){
                yLoc = this.randYLoc();
                xLoc = verSpawnCoordinates.get(yLoc);
            }
            FastEnemy fastEnemy = new FastEnemy(xLoc, yLoc);
            return fastEnemy;
        }
        else if (enemyType.equals("damageenemy")){ // Random damage enemy
            if (borderPlace == 1){
                xLoc = this.randXLoc();
                yLoc = horSpawnCoordinates.get(xLoc);
            } else if (borderPlace == 2){
                yLoc = this.randYLoc();
                xLoc = verSpawnCoordinates.get(yLoc);
            }
            DamageEnemy damageEnemy = new DamageEnemy(xLoc, yLoc);
            return damageEnemy;
        }
        return null;
    }
    public ArrayList<LargeEnemy> getLargeEnemies(){ // Gets the large enemies in the round
        ArrayList<LargeEnemy> largeEnemies = new ArrayList<LargeEnemy>();
        for (int i = 0; i < this.enemies.size(); i++){
            if (this.enemies.get(i) instanceof LargeEnemy){
                largeEnemies.add((LargeEnemy)this.enemies.get(i));
            } else {
                largeEnemies.add(null);
            }
        }
        return largeEnemies;
    }
    public void moveEnemiesToward(Player player){ // Moves all the enemies towards the player
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).moveToward(player);
            if (this.getLargeEnemies().get(i)!=null && this.getLargeEnemies().get(i).getShield()!=null && enemies.get(i) instanceof LargeEnemy){
                this.getLargeEnemies().get(i).moveShield();
            }
            if (enemies.get(i) instanceof FastEnemy){
                if (((FastEnemy)enemies.get(i)).isLow()){   // If the enemy is low
                    ((FastEnemy)enemies.get(i)).setSpeed(4);    // Increase the speed
                }
            }
            if (enemies.get(i) instanceof DamageEnemy){
                if (((DamageEnemy)enemies.get(i)).isLow()){   // If the enemy is low
                    ((DamageEnemy)enemies.get(i)).throwExplosive(player);   // throw the explosive
                }else{
                    ((DamageEnemy)enemies.get(i)).moveExplosive();
                }
            }
        }
    }
    public void drawEnemies(Graphics g){    // Draws all the enemies in the round
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
            if (this.getLargeEnemies().get(i)!=null && this.getLargeEnemies().get(i).getShield()!=null && enemies.get(i) instanceof LargeEnemy){
                this.getLargeEnemies().get(i).drawShield(g);
            }
            if (enemies.get(i) instanceof DamageEnemy){
                if (((DamageEnemy)enemies.get(i)).isLow()){
                    ((DamageEnemy)enemies.get(i)).drawExplosive(g);
                }
            }
        }
    }
    public boolean isCleared(){ // Checks if the round is cleared
        if (this.enemies.size() == 0){ // If no enemies remain
            return true;               // Return true
        }
        return false;
    }
    public void removeEnemy(int index){ // Removes an enemy in the round
        this.enemies.remove(index);
    }
    public void removeAll(){
        for (int i = 0; i < this.enemies.size(); i++){ // Removes all the enemies
            this.enemies.remove(i);
        }
    }
    public int randXLoc(){ // Generates random x location
        return ((int)((Const.FRAME_WIDTH/20)*Math.random())) * 20;
    }
    public int randYLoc(){ // Generates random y location
        return ((int)((Const.FRAME_HEIGHT/20)*Math.random())) * 20;
    }
    public Map<Integer,Integer> genHorSpawnCoordinates(){   // Generates random horizontal border spawn coordinates
        Map<Integer,Integer> horSpawnCoordinates = new TreeMap<Integer,Integer>();
        int yLoc = 0;
        for (int i = 0; i < Const.FRAME_WIDTH; i+=20){
            int randY = (int)((2)*Math.random()+1);
            if (randY == 1){    // Spawn on the top border
                yLoc = 0;
            } else if (randY == 2){ // Spawn on the bot border
                yLoc = Const.FRAME_HEIGHT;
            }
            horSpawnCoordinates.put(i, yLoc);
        }        
        return horSpawnCoordinates;
    }
    public Map<Integer,Integer> genVerSpawnCoordinates(){   // Generates random vertical border spawn coordinates
        Map<Integer,Integer> verSpawnCoordinates = new TreeMap<Integer,Integer>();
        int xLoc = 0;
        for (int i = 0; i < Const.FRAME_HEIGHT; i+=20){
            int randX = (int)((2)*Math.random()+1);
            if (randX == 1){    // Spawn on the left border
                xLoc = 0;
            } else if (randX == 2){ // Spawn on the right border
                xLoc = Const.FRAME_WIDTH;
            }
            verSpawnCoordinates.put(i, xLoc);
        }        
        return verSpawnCoordinates;
    }
    // Getters and Setters
    public boolean getSpawned(){
        return this.spawned;
    }
    public void setSpawned(boolean spawned){
        this.spawned = spawned;
    }
    public int getRoundNum(){
        return this.roundNum;
    }
    public void setRoundNum(int roundNum){
        this.roundNum = roundNum;
    }
    public ArrayList<Enemy> getEnemies(){
        return this.enemies;
    }
    public boolean getFinalRoundFinished(){
        return this.finalRound;
    }
    public void setfinalRoundFinished(boolean finalRound){
        this.finalRound = finalRound;
    }
}
