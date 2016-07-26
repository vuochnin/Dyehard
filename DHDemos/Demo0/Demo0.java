package Demo0;
import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;

/**
 * 
 * @author vuochnin
 *
 *
 *	Demonstrates basic Hero functionality and Input functionality
 */
public class Demo0 extends DHProceduralAPI
{
	int controlSelect = 0;
	int heroID;
	
	public void buildGame()
	{
		// Demonstrate an API function
		heroID = apiStartHero();
	}
	
	public void updateGame()
	{
		if(apiIsKeyboardButtonTapped(KeysEnum.ONE))
		{
			controlSelect = 0;
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.TWO))
		{
			controlSelect = 1;
		}
		switch(controlSelect)
		{
		case 0:
			apiObjectFollowTheMouse(heroID);
			break;
		case 1:
			if(apiIsKeyboardUpPressed())
				apiMoveObject(heroID, 0, 1);
			if(apiIsKeyboardDownPressed())
				apiMoveObject(heroID, 0, -1);
			if(apiIsKeyboardLeftPressed())
				apiMoveObject(heroID, -1, 0);
			if(apiIsKeyboardRightPressed())
				apiMoveObject(heroID, 1, 0);
			break;
		}
	}
}