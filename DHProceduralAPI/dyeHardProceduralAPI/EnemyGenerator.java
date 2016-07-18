package dyeHardProceduralAPI;

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
    
	static{
		interval = 3;
		active = false;
	}
	
	
    public static void initialize(Hero hero) {
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
    private static Vector2 randomPosition(){
    	float randomY = RANDOM.nextInt((int) BaseCode.world.getHeight() - 8) + 5;
        Vector2 position = new Vector2(BaseCode.world.getWidth() + 10, randomY);
        return position;
    }
    
    public static int spawnEnemy(){
		if (hero == null)
		{
			System.err.println("EnemyGenerator: Hero not initialized!");
			return -1;
		}
	    Enemy result;

		switch (RANDOM.nextInt(5))
		{
        //case 1:
            //result = (new PortalEnemy(randomPosition(), hero));
            //break;
        case 2:
	        result = (new ChargerEnemy(randomPosition(), hero));
            break;
        case 3:
	        result = (new ShootingEnemy(randomPosition(), hero));
            break;
        case 4:
	        result = (new CollectorEnemy(randomPosition(), hero));
            break;
        default:
	        result = (new RegularEnemy(randomPosition(), hero));
            break;
        }
	    EnemyManager.getInstance().registerEnemy(result);
	    return IDManager.register(result);
    }
    
    public static int spawnEnemy(String EnemyType){
	    if (hero == null)
	    {
		    System.err.println("EnemyGenerator: Hero not initialized!");
		    return -1;
	    }

    	String type = EnemyType.toLowerCase();

		Enemy result;

    	switch(type){
    	case "portal":
    		result = (new PortalEnemy(randomPosition(), hero));
            break;
    	case "charger":
		    result = (new ChargerEnemy(randomPosition(), hero));
            break;
        case "shooting":
	        result = (new ShootingEnemy(randomPosition(), hero));
            break;
        case "collector":
	        result = (new CollectorEnemy(randomPosition(), hero));
            break;
        default:
	        result = (new RegularEnemy(randomPosition(), hero));
            break;
    	}
	    EnemyManager.getInstance().registerEnemy(result);
	    return IDManager.register(result);
    }
    
    public static int spawnEnemy(float height){
	    if (hero == null)
	    {
		    System.err.println("EnemyGenerator: Hero not initialized!");
		    return -1;
	    }

        Vector2 position = new Vector2(BaseCode.world.getWidth(), height);
	    Enemy result;

	    switch (RANDOM.nextInt(5))
	    {
	    //case 1:
	        //result = (new PortalEnemy(position, hero));
	        //break;
	    case 2:
		    result = (new ChargerEnemy(position, hero));
		    break;
	    case 3:
		    result = (new ShootingEnemy(position, hero));
		    break;
	    case 4:
		    result = (new CollectorEnemy(position, hero));
		    break;
	    default:
		    result = (new RegularEnemy(position, hero));
		    break;
	    }
	    EnemyManager.getInstance().registerEnemy(result);
	    return IDManager.register(result);
    }

    public static int enemyCount(){
    	return EnemyManager.getInstance().getEnemies().size();
    }

    public static void update() {
        if (active && TimeManager.repeatingTimer("ENEMY_GENERATION", interval)) {
            spawnEnemy();
        }
    }
    
    public void clearEnemy() {
    	EnemyManager.getInstance().clear();
    }
}
