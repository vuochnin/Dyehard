import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * Lab4: Introduce If/Else statement and variable declaration
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
	int cursor;	// This is the id of the object that will serve as a cursor
	
	
	public void buildGame(){
		apiEcho("Use arrow keys to move the cursor.");
		cursor = apiSpawnSingleDyePack("red", x, y);

	}
	
	public void updateGame(){

		// if drawingMode is 'true', the pen is against the paper
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

		// This function uses the variables to move the pen
		apiMoveObjectTo(cursor, x, y);
	}
}
