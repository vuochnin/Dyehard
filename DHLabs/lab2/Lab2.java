package lab2;
import dyeHardProceduralAPI.DHProceduralAPI;

/**
 * Lab2: Spawning Debris object with parameters.
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab2 extends DHProceduralAPI
{
	public void buildGame()
	{
		apiSpawnSingleDebris(100, 50);			// x position = 100, y position = 50
		apiSpawnSingleDebris(100, 40);			// x position = 100, y position = 40
		apiSpawnSingleDebris(100, 30);			// x position = 100, y position = 30
		apiSpawnSingleDebris(100, 20);			// x position = 100, y position = 20
		apiSpawnSingleDebris(60, 30);			// x position = 60, y position = 30
		apiSpawnSingleDebris(70, 20, false);	// x position = 70, y position = 20, startMoving = false
	}
}
