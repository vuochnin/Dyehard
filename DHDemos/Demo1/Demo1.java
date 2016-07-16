package Demo1;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates basic Hero functionality and Input functionality
 */
public class Demo1 extends DHProceduralAPI
{
	int controlSelect = 0;
	
	public void buildGame()
	{
		// Demonstrate an API function
		startHero();
	}
	
	public void updateGame()
	{
		if(isKeyboardButtonTapped(KeysEnum.ONE))
		{
			controlSelect = 0;
		}
		if(isKeyboardButtonTapped(KeysEnum.TWO))
		{
			controlSelect = 1;
		}
		switch(controlSelect)
		{
		case 0:
			heroFollowTheMouse();
			break;
		case 1:
			if(isKeyboardUpPressed())
				moveUp();
			if(isKeyboardDownPressed())
				moveDown();
			if(isKeyboardLeftPressed())
				moveLeft();
			if(isKeyboardRightPressed())
				moveRight();
			break;
		}
	}
}
