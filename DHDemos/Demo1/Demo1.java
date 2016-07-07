package Demo1;
import dyeHardProcedrualAPI.DHProcedrualAPI;

/**
 * 
 * @author vuochnin
 *
 */
public class Demo1 extends DHProcedrualAPI{

	public void buildGame()
	{
		// Demonstrate an API function
		spawnSingleDebris();
		spawnSingleDebris();
		spawnSingleDebris();
		spawnSingleDebris(10);
		spawnSingleDebris(15);
		spawnSingleDebris(20);
		spawnSingleDebris(25);
	}
}
