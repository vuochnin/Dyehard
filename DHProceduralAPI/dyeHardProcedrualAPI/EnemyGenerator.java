package dyeHardProcedrualAPI;

import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Enemies.*;
import dyehard.Player.Hero;


public class EnemyGenerator {
    private static Random RANDOM = new Random();
    private static Hero hero;
    
    private static float        interval;
	private static boolean      active;
	private static int 			count;
    
	static{
		interval = 3;
		active = false;
		count = 0;
	}
	
	
    public EnemyGenerator(Hero hero) {
        EnemyGenerator.hero = hero;
    }
    
    public static void enable(){
    	active = true;
    }
    
    public static void disable(){
    	active = false;
    }
    
    /**
	 * Sets the spawn interval of enemy
	 * @param seconds the time in seconds
	 */
	public static void setInterval(float seconds)
	{
		interval = seconds;
	}
    
	/**
	 * Generate a random position for the each enemy
	 * @return the position of the enemy as Vector2
	 */
    private Vector2 randomPosition(){
    	float randomY = RANDOM.nextInt((int) BaseCode.world.getHeight() - 8) + 5;
        Vector2 position = new Vector2(BaseCode.world.getWidth() + 10, randomY);
        return position;
    }
    
    public void spawnEnemy(){
		switch (RANDOM.nextInt(5)) {
        case 1:
            //EnemyManager.getInstance().registerEnemy(new PortalEnemy(randomPosition(), hero));
        	count++;
            break;
        case 2:
        	EnemyManager.getInstance().registerEnemy(new ChargerEnemy(randomPosition(), hero));
        	count++;
            break;
        case 3:
        	EnemyManager.getInstance().registerEnemy(new ShootingEnemy(randomPosition(), hero));
        	count++;
            break;
        case 4:
        	EnemyManager.getInstance().registerEnemy(new CollectorEnemy(randomPosition(), hero));
        	count++;
            break;
        default:
        	EnemyManager.getInstance().registerEnemy(new RegularEnemy(randomPosition(), hero));
        	count++;
            break;
        }
    }
    
    public void spawnEnemy(String EnemyType){
    	String type = EnemyType.toLowerCase();
    	switch(type){
    	case "portal":
    		EnemyManager.getInstance().registerEnemy(new PortalEnemy(randomPosition(), hero));
        	count++;
            break;
    	case "charger":
        	EnemyManager.getInstance().registerEnemy(new ChargerEnemy(randomPosition(), hero));
        	count++;
            break;
        case "shooting":
        	EnemyManager.getInstance().registerEnemy(new ShootingEnemy(randomPosition(), hero));
        	count++;
            break;
        case "collector":
        	EnemyManager.getInstance().registerEnemy(new CollectorEnemy(randomPosition(), hero));
        	count++;
            break;
        case "regular":
        	EnemyManager.getInstance().registerEnemy(new RegularEnemy(randomPosition(), hero));
        	count++;
            break;
        default:
        	System.out.println("ERROR: The type of enemy you entered is not existed");
        	break;
    	}
    }
    
    public void spawnEnemy(float height){
        Vector2 position = new Vector2(BaseCode.world.getWidth(), height);
		switch (RANDOM.nextInt(5)) {
        case 1:
            //EnemyManager.getInstance().registerEnemy(new PortalEnemy(position, hero));
        	count++;
            break;
        case 2:
        	EnemyManager.getInstance().registerEnemy(new ChargerEnemy(position, hero));
        	count++;
            break;
        case 3:
        	EnemyManager.getInstance().registerEnemy(new ShootingEnemy(position, hero));
        	count++;
            break;
        case 4:
        	EnemyManager.getInstance().registerEnemy(new CollectorEnemy(position, hero));
        	count++;
            break;
        default:
        	EnemyManager.getInstance().registerEnemy(new RegularEnemy(position, hero));
        	count++;
            break;
        }
    }

    public int enemyCount(){
    	return count;
    }

    public void update() {
        if (active && TimeManager.repeatingTimer("ENEMY_GENERATION", interval)) {
            spawnEnemy();
        }
    }
    
    public void clearEnemy() {
    	EnemyManager.getInstance().clear();
    }
}
