package Demo1;
import dyeHardProcedrualAPI.DHProceduralADI;

/**
 * 
 * @author vuochnin
 *
 */
public class Demo1 extends DHProceduralADI
{

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
