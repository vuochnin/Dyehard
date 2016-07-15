package Demo2;
import dyeHardProcedrualAPI.DHProceduralAPI;
import dyeHardProcedrualAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 * Demonstrates basic 
 */
public class Demo2 extends DHProceduralAPI
{

	public void buildGame()
	{
		startHero();
		
		 // Start an object spawner with the default setting
		startDebrisSpawner(0.3f);
		
		// Start object spawners with custom settings
		startDyePackSpawner(3);
		startEnemySpawner(2.5f);
		
	}
	
	public void updateGame()
	{
		heroFollowTheMouse();
		
		if(isMouseLeftClicked())
			firePaint();
		
		for(int i = 0; i < objectCount(); i++)
		{
			if(getType(i) == "Debris")
			{
				//move(i,0,-0.1f);
			}
		}
	}
	
	// Define custom collision code
	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
	{
		// Examine the types of the colliding objects
		if(type1 == "Debris" && type2 == "Debris")
		{
			move(id1,-2,-4);
			move(id2, 2, 4); // This function overrides default collision code
		}
		
		if(type1 == "Bullet" && subtype2 == "Charger")
		{
			destroy(id1);
			destroy(id2);
		}
		
		if(type1 == "Hero" && subtype2 == "Shooting")
		{
			doNothing(); // prevent default collision behavior
		}
		
		// DyePacks will have default behavior on collision
	}
	
}
