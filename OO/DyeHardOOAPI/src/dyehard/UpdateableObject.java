package dyehard;

import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateableObject.
 */
public abstract class UpdateableObject implements Updateable {

    /**
     * Instantiates a new updateable object.
     */
    public UpdateableObject() {
        UpdateManager.getInstance().register(this);
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#updateState()
     */
    @Override
    public ManagerStateEnum updateState() {
        return ManagerStateEnum.ACTIVE;
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#update()
     */
    @Override
    public abstract void update();
}