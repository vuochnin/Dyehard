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
		
	float x = 2;	// int can also be used here
	float y = 2;	// int can also be used here
	boolean drawingMode = false;
	int cursor;	
	
	
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
		if(drawingMode){
			if(apiIsKeyboardButtonTapped(KeysEnum.UP)){
				y = y + 3;
				apiMoveObject(cursor, 0, 3);
				apiSpawnSingleDyePack("blue", x, y);
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.DOWN)){
				y = y - 3;
				apiMoveObject(cursor, 0, -3);
				apiSpawnSingleDyePack("teal", x, y);
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.RIGHT)){
				x = x + 3;
				apiMoveObject(cursor, 3, 0);
				apiSpawnSingleDyePack("pink", x, y);
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.LEFT)){
				x = x - 3;
				apiMoveObject(cursor, -3, 0);
				apiSpawnSingleDyePack("green", x, y);
			}
		}
		else{
			if(apiIsKeyboardButtonTapped(KeysEnum.UP)){
				y = y + 3;
				apiMoveObject(cursor, 0, 3);
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.DOWN)){
				y = y - 3;
				apiMoveObject(cursor, 0, -3);
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.RIGHT)){
				x = x + 3;
				apiMoveObject(cursor, 3, 0);
			}
			else if(apiIsKeyboardButtonTapped(KeysEnum.LEFT)){
				x = x - 3;
				apiMoveObject(cursor, -3, 0);
			}
		}
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
