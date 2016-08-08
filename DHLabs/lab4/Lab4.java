package lab4;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab3: Graphic Editor
 * Introduce If/Else statement and variable declaration
 * 
 *	@author Holden
 *	@author Nin
 */
public class Lab4 extends DHProceduralAPI
{
	// Variable declarations

	// These numbers represent the position of the pen
	float x = 2;	// int can also be used here
	float y = 2;	// int can also be used here

	boolean drawingMode = false;

	int cursor;	// This is the id of the object that will serve as a cursor
	
	
	public void buildGame(){
		apiEcho("Use arrow keys to move the cursor. \nTap on SPACE BAR to toggle the drawing mode");
		cursor = apiSpawnSingleDyePack("red", x, y);
		apiAlwaysOnTop(cursor);
		//buildTheWorld();
	}
	
	public void updateGame(){

		if(apiIsKeyboardButtonTapped(KeysEnum.SPACE)){
			drawingMode = !drawingMode;					// Switching drawingMode on and off
		}

		if(drawingMode)
		{   // if drawingMode is 'true', the pen is against the paper
			if(apiIsKeyboardButtonTapped(KeysEnum.UP)){
				// Place a dyepack as 'ink'
				apiSpawnSingleDyePack("blue", x, y);

				// move the variable for the pen's position upward
				y = y + 3;
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.DOWN)){
				apiSpawnSingleDyePack("teal", x, y);

				y = y - 3;// downward
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.RIGHT)){
				apiSpawnSingleDyePack("pink", x, y);

				x = x + 3; // rightward
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.LEFT)){
				apiSpawnSingleDyePack("green", x, y);

				x = x - 3; // leftward
			}
		}
		else // drawingMode is false, the pen is off the paper
		{
			if(apiIsKeyboardButtonTapped(KeysEnum.UP)){
				y = y + 3;
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.DOWN)){
				y = y - 3;
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.RIGHT)){
				x = x + 3;
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.LEFT)){
				x = x - 3;
			}
		}

		// This function uses the variables to move the pen
		apiMoveObjectTo(cursor, x, y);
	}
	
//	public void buildTheWorld(){
//		apiSpawnSingleDebris(3, 8);
//		apiSpawnSingleDebris(3, 14);
//		apiSpawnSingleDebris(3, 20);
//		apiSpawnSingleDebris(3, 26);
//		apiSpawnSingleDebris(3, 32);
//		apiSpawnSingleDebris(3, 38);
//		apiSpawnSingleDebris(3, 44);
//	}
}
