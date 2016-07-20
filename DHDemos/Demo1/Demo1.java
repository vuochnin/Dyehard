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
	int heroID;
	
	public void buildGame()
	{
		// Demonstrate an API function
		heroID = startHero();
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
			API_ObjectFollowTheMouse(heroID);
			break;
		case 1:
			if(isKeyboardUpPressed())
				API_MoveObject(heroID, 0, 1);
			if(isKeyboardDownPressed())
				API_MoveObject(heroID, 0, -1);
			if(isKeyboardLeftPressed())
				API_MoveObject(heroID, -1, 0);
			if(isKeyboardRightPressed())
				API_MoveObject(heroID, 1, 0);
			break;
		}
	}
}
