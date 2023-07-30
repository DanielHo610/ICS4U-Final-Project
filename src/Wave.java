import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Wave {
    private ArrayList<String> waveSpawnList;
    private int waveNum = 0;
    private Round round;
    private boolean spawned;
    private boolean cleared;
    public Wave(int waveNum, int roundNums, boolean spawned, boolean cleared) throws Exception{
        this.waveNum = waveNum;
        this.spawned = spawned;
        this.cleared = cleared;
        this.round = new Round(0, spawned);
        this.waveSpawnList = readWaveList(this.waveNum);
    }
    public void checkCollisions(Player player, Weapon weapon){  // Checks the collisions in the wave
        for (int i = 0; i < this.getRound().getEnemies().size(); i++){
            if (this.getRound().getEnemies().get(i).collides(player)){          // If the enemy hits the player
                player.reducePlayerHealth(this.getRound().getEnemies().get(i)); // Reduce the players health by the enemies damage
            }
            if (this.getRound().getEnemies().get(i) instanceof DamageEnemy){
                if (((DamageEnemy)this.getRound().getEnemies().get(i)).getExplosive().collides(player)){
                    player.setHealth(0);
                }
            }
            if (this.getRound().getEnemies().get(i) instanceof LargeEnemy){     // Checks if the shield is hit
               if (((LargeEnemy) this.getRound().getEnemies().get(i)).getShield()!=null && 
               ((LargeEnemy) this.getRound().getEnemies().get(i)).shieldHit(weapon.getMagazine())){
                ((LargeEnemy) this.getRound().getEnemies().get(i)).reduceShieldHealth();
               }
               if (((LargeEnemy) this.getRound().getEnemies().get(i)).getShield()!=null && 
               ((LargeEnemy) this.getRound().getEnemies().get(i)).shieldIsBroken()){    // Checks if the shield is broken
                ((LargeEnemy) this.getRound().getEnemies().get(i)).removeShield();
               }
            }
            if (this.getRound().getEnemies().get(i).isHit(weapon.getMagazine())){   // Checks if the enemy is killed
                this.getRound().getEnemies().get(i).reduceHealth();
                if (this.getRound().getEnemies().get(i).isKilled(this.getRound().getEnemies())){
                    this.getRound().removeEnemy(i);
                }
            }
        }
    }
    public void spawnRound(int roundNum) { // Spawns the round
        this.round = new Round (roundNum, false);
        this.round.spawn(this.waveSpawnList);
    }
    public ArrayList<String> readWaveList(int waveNum) throws Exception{ // Reads the wave numbers text file
        ArrayList<String> waveSpawnList = new ArrayList<String>();       // to see what enemies need to be spawned and the number of rounds
        String header="";
        String fileName = "wave"+(waveNum+1);
        final String WAVE_FILE = "waves/"+fileName+".txt";
        File waveFile = new File(WAVE_FILE);
        Scanner input = new Scanner(waveFile);
        while (input.hasNext() && !header.equals("waveclear")){               
            header = input.nextLine();
            waveSpawnList.add(header);
        }
        input.close();
        return waveSpawnList;
    }
    public boolean isFinalWave() { // Checks if it is the final wave
        if (this.waveNum == Const.FINAL_WAVE){
            return true;
        }
        return false;
    }
    // Getters and Setters
    public int getRoundNums(){  // Gets the number of rounds in the wave
        int roundNums = 0;
        for (int i = 0; i < waveSpawnList.size(); i++){
            if (waveSpawnList.get(i).substring(0,1).equals("r")){
                roundNums++;
            }
        }
        return roundNums;
    }
    public ArrayList<String> getWaveSpawnList(){
        return this.waveSpawnList;
    }
    public void setWaveSpawnList(ArrayList<String> waveSpawList){
        this.waveSpawnList = waveSpawList;
    }
    public int getWaveNum(){
        return this.waveNum;
    }
    public void setWaveNum(int waveNum){
        this.waveNum = waveNum;
    }
    public boolean getSpawned(){
        return this.spawned;
    }
    public void setSpawned(boolean spawned){
        this.spawned = spawned;
    }
    public Round getRound(){
        return this.round;
    }
    public void setCleared(boolean cleared){
        this.cleared = cleared;
    }
    public boolean getCleared(){
        return this.cleared;
    }

}
