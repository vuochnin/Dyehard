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
		// Demonstrate an API function
		startHero();
		startDebrisSpawner();
		startDyePackSpawner(3);
		startEnemySpawner(2);
		
	}
	
	public void updateGame()
	{
		heroFollowTheMouse();
		
		if(isMouseLeftClicked())
			firePaint();
	}
	
	// Demonstrate custom collision code
	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
	{
		if(type1 == "Hero" && type2 == "Debris")
		{
			move(id2, 2, 4); // This function overrides default collision code
		}
		
		if(type1 == "Bullet" && subtype2 == "Charger")
		{
			destroy(id1);
			destroy(id2);
		}
		
		if(type1 == "Hero" && subtype2 == "Shooting")
		{
			doNothing(); // prevent default behavior
		}
		
		// DyePacks will have default behavior on collision
	}
	
}
