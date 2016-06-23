package dyehard.GameScreens;

import Engine.BaseCode;
import dyehard.DyehardRectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditBack.
 */
public class CreditBack extends DyehardRectangle {

	/**
	 * Instantiates a new credit back.
	 */
	public CreditBack() {
		
		size.set(BaseCode.world.getWidth(),
                BaseCode.world.getHeight());
		
		center.set(BaseCode.world.getWidth() / 2,
                BaseCode.world.getHeight() / 2);
		
		texture = BaseCode.resources
                .loadImage("Textures/UI/DyeHard_CreditsBack.png");
		
		visible = false;
	}
}
