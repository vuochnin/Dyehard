package dyehard.Player;

import Engine.Vector2;
import dyehard.DyehardRectangle;
import dyehard.UpdateableObject;
import dyehard.Resources.DyeHardResources;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.Weapon;

// TODO: Auto-generated Javadoc
/**
 * The Class DyeMeter.
 */
public class DyeMeter extends UpdateableObject {

    /**
     * The Interface Progressable.
     */
    public interface Progressable {
        
        /**
         * Current value.
         *
         * @return the int
         */
        public int currentValue();

        /**
         * Total value.
         *
         * @return the int
         */
        public int totalValue();
    }

    /** The hero. */
    protected Hero hero;
    
    /** The frame. */
    DyehardRectangle frame; // the frame containing the meter    
    
    /** The meter. */
    DyehardRectangle meter; // the meter that fills up depending on the weapon    
    
    /** The Constant OFFSET. */
    protected static final Vector2 OFFSET = new Vector2(-5.0f, 0f);

    /**
     * Instantiates a new dye meter.
     *
     * @param hero is the new hero
     */
    public DyeMeter(Hero hero) {
        this.hero = hero;

        frame = DyeHardResources.getInstance().getScaledAnimation(new Vector2(1920, 1080), new Vector2(72,
                208), 6, 2, "Textures/UI/dye_meter_frame_anim.png");

        meter = DyeHardResources.getInstance().getScaledAnimation(new Vector2(1920, 1080), new Vector2(72,
                208), 20, 2, "Textures/UI/dye_meter_fill_anim.png");
    }

    /** The i. */
    static int i = 0;

    /* (non-Javadoc)
     * @see dyehard.UpdateableObject#update()
     */
    @Override
    public void update() {
        Weapon weapon = hero.currentWeapon;
        float meterPercent = 1f; // the amount of the meter shown

        if (weapon.totalValue() != 0) {
            meterPercent = (float) weapon.currentValue()
                    / (float) weapon.totalValue();
            meterPercent = Math.min(meterPercent, 1.0f);
            meterPercent = Math.max(meterPercent, 0f);
        }

        if (weapon instanceof OverHeatWeapon && weapon.currentValue() == 0) {
            frame.visible = false;
            meter.visible = false;
        } else {
            frame.visible = true;
            meter.visible = true;
        }

        frame.center = hero.center.clone();
        frame.center.offset(OFFSET);

        meter.center = hero.center.clone();
        meter.center.offset(OFFSET);

        meter.setFrameNumber((int) (meter.getNumFrames() * meterPercent));
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#setSpeed(float)
     */
    @Override
    public void setSpeed(float v) {
        // TODO Auto-generated method stub

    }
}
