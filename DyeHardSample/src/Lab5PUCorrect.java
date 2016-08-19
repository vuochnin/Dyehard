import java.awt.Color;

import Engine.BaseCode;
import dyehard.Collectibles.PowerUp;
import dyehard.Player.Hero;
import dyehard.Weapons.*;

/**
 * Lab 5 - New Powerup that equips a new weapon 
 * 
 * @author Sammy
 *
 */
public class Lab5PUCorrect extends PowerUp{
	
	/** The new weapon */
	Weapon newWep;
	
	public Lab5PUCorrect(){
		super();
		texture = BaseCode.resources.loadImage("Textures/PowerUp_Magnetism.png");
	}
	
	/**
	 * Method to set a new weapon
	 * @param newWeaponObject
	 */
	public void setWeapon(Weapon newWeaponObject) {
		newWep = newWeaponObject;
	}

	@Override
	public void apply(Hero hero) {
		hero.currentWeapon = newWep;
	}

	@Override
	public void unapply(Hero hero) {
		hero.currentWeapon = new OverHeatWeapon(hero);
	}

	@Override
	public PowerUp clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
