package dyehard;

import Engine.Vector2;
import dyehard.Updateable;
import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class DyeHardGameObject.
 */
public class DyeHardGameObject extends DyehardRectangle implements Updateable {
    
    /** The speed up set. */
    private boolean speedUpSet = false;
    
    /** The update state. */
    private ManagerStateEnum updateState;

    /**
     * Instantiates a new dye hard game object.
     */
    public DyeHardGameObject() {
        updateState = ManagerStateEnum.ACTIVE;
        UpdateManager.getInstance().register(this);
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#updateState()
     */
    @Override
    public ManagerStateEnum updateState() {
        return updateState;
    }

    /* (non-Javadoc)
     * @see dyehard.DyehardRectangle#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();
        updateState = ManagerStateEnum.DESTROYED;
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#setSpeed(float)
     */
    @Override
    public void setSpeed(float factor) {
        // TODO For debug purposes, maybe remove for final version
        if ((factor > 1 && !speedUpSet) || (factor < 1 && speedUpSet)) {
            float curSpeed = velocity.getX();
            velocity = new Vector2(curSpeed * factor, 0f);
            if (factor > 1) {
                speedUpSet = true;
            } else if (factor < 1) {
                speedUpSet = false;
            }
        }
    }
}
