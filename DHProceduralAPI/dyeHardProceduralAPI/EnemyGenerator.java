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
	
	/**
	 * Register the hero with the EnemyGenerator
	 * @param hero the initialized hero
	 */
    public static void initialize(Hero hero) {
        EnemyGenerator.hero = hero;
    }
    
    /**
     * Enables the EnemyGenerator
     */
    public static void enable(){
    	active = true;
    }
    
    /**
     * Disables the EnemyGenerator
     */
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
	 * Generate a random position on the right edge of the window for the each enemy
	 * @return the position of the enemy as Vector2
	 */
    private static Vector2 randomPosition(){
    	float randomY = RANDOM.nextInt((int) BaseCode.world.getHeight() - 8) + 5;
        Vector2 position = new Vector2(BaseCode.world.getWidth() + 10, randomY);
        return position;
    }
    
    /**
     * Generate a random enemy at a random location
     * on the right edge of the window
     * @return the id of the enemy as integer
     */
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
    
    /**
     * Generate an enemy with the specified type at a random location
     * on the right edge of the window
     * @param EnemyType the type of the enemy (as a string)
     * @return the id of the enemy as integer
     */
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
    
    /**
     * Generates an enemy with the specified type at a specified location
     * @param EnemyType the type of the enemy (as a string)
     * @param x the x-coordinate position
     * @param y the y-coordinate position
     * @return the id of the enemy as integer
     */
    public static int spawnEnemy(String EnemyType, float x, float y){
	    if (hero == null)
	    {
		    System.err.println("EnemyGenerator: Hero not initialized!");
		    return -1;
	    }

    	String type = EnemyType.toLowerCase();
    	Vector2 pos = new Vector2(x, y);
		Enemy result;

    	switch(type){
    	case "portal":
    		result = (new PortalEnemy(pos, hero));
            break;
    	case "charger":
		    result = (new ChargerEnemy(pos, hero));
            break;
        case "shooting":
	        result = (new ShootingEnemy(pos, hero));
            break;
        case "collector":
	        result = (new CollectorEnemy(pos, hero));
            break;
        default:
	        result = (new RegularEnemy(pos, hero));
            break;
    	}
	    EnemyManager.getInstance().registerEnemy(result);
	    return IDManager.register(result);
    }
    
    
    /**
     * Generates a random enemy on the right side of the window with the 
     * specified y-coordinate position 
     * @param y the y-coordinate position
     * @return the id of the enemy as integer
     */
    public static int spawnEnemy(float x, float y){
	    if (hero == null)
	    {
		    System.err.println("EnemyGenerator: Hero not initialized!");
		    return -1;
	    }

        Vector2 position = new Vector2(x, y);
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

    /**
     * Reports the number of enemies instantiated
     * @return the number of enemies instantiated
     */
    public static int enemyCount(){
    	return EnemyManager.getInstance().getEnemies().size();
    }

    /**
     * Updates the EnemyGenerator
     */
    public static void update() {
        if (active && TimeManager.repeatingTimer("ENEMY_GENERATION", interval)) {
            spawnEnemy();
        }
    }
    
    /**
     * Clear every enemy from the game
     */
    public static void clearEnemy() {
    	EnemyManager.getInstance().clear();
    }
}
